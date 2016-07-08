package com.cat.manage.base.service;

import java.util.List;
import java.util.Map;

import javax.swing.SingleSelectionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.base.dao.SingleproductDao;
import com.cat.manage.base.domain.Series;
import com.cat.manage.base.domain.Singleproduct;
import com.cat.manage.base.domain.SingleproductHistory;
import com.cat.manage.common.exception.BusinessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;

/**
 * 单品服务
 * @author wanghang
 *
 */
@Service
public class SingleproductService {
	@Autowired
	private SingleproductDao singleproductDao;
	
	@Autowired
	private SeriesService seriesService;
	
	@Autowired
	private SingleproductHistoryService singleHisService;
	
	@Autowired
	private SyncDirectoryService syncDirService;
	
	/**
	 * 添加单品信息
	 * @param singleproduct
	 */
	public boolean addSingleproduct(Singleproduct singleproduct){
		Series Series = seriesService.querySeriesById(Integer.valueOf(singleproduct.getOfOrigin()));
		if(Series == null)
			throw new BusinessException("1", "系列信息不存在");
		if(!"1".equals(Series.getIsUse()))
			throw new BusinessException("1", "系列已失效");
		
		//校验唯一性
		List<Singleproduct> list = singleproductDao.querySingleproductsAccurateForName(singleproduct);
		if(list != null && list.size() >= 1)
			return false;
		
		singleproductDao.addSingleproduct(singleproduct);
		return true;
	}
	
	/**
	 * 修改单品信息
	 * @param singleproduct
	 */
	public boolean updateSingleproduct(Singleproduct singleproduct){
		Series Series = seriesService.querySeriesById(Integer.valueOf(singleproduct.getOfOrigin()));
		if(Series == null)
			throw new BusinessException("1", "系列信息不存在");
		if(!"1".equals(Series.getIsUse()))
			throw new BusinessException("1", "系列已失效");
		
		//校验唯一性
		List<Singleproduct> list = singleproductDao.querySingleproductsAccurateForName(singleproduct);
		if(list != null && list.size() >= 1)
			return false;
		
		//添加历史记录
		this.addOrUpdateSingleproductHistoryInfo(singleproduct);
		
		singleproductDao.updateSingleproduct(singleproduct);
		return true;
	}
	
	/**
	 * 删除单品信息
	 * @param singleproductId
	 */
	public void deleteSingleproduct(Integer singleId){
		//删除历史记录
		singleHisService.deleteSingleproductHistory(singleId);
		
		singleproductDao.deleteSingleproduct(singleId);
	}
	
	/**
	 * 根据系列编号删除单品
	 * @param seriesIds
	 */
	public void deleteSingleproductBySeriesId(Integer[] seriesIds){
		//根据系列唯一编号删除单品历史记录
		for(Integer seriesId : seriesIds){
			singleHisService.deleteSingleproductHistoryBySeriesId(seriesId);
		}
		
		singleproductDao.deleteSingleproductBySeriesIds(seriesIds);
	}
	
	/**
	 * 查询单品信息
	 * @param singleproduct
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Singleproduct> querySingleproduct(Singleproduct singleproduct, Integer pageNum, Integer pageSize, String brandId){
		PageHelper.startPage(pageNum, pageSize);
		List<Singleproduct> list = singleproductDao.querySingleproducts(singleproduct, brandId);
		PageInfo<Singleproduct> page = new PageInfo<Singleproduct>(list);
		return page;
	}
	
	/**
	 * 查询所有单品信息
	 * @param singleproduct
	 * @param brandId
	 * @return
	 */
	public List<Singleproduct> querySingleproductAll(Singleproduct singleproduct, String brandId){
		return singleproductDao.querySingleproducts(singleproduct, brandId);
	}
	
	/**
	 * 通过单品编号查询单品信息
	 * @param singleId
	 * @return
	 */
	public Singleproduct querySingleproductBySingleId(Integer singleId){
		return singleproductDao.querySingleproductBySingleId(singleId);
	}
	
	/**
	 * 恢复单品历史记录
	 * @param single
	 */
	public void recoverSingleproduct(Singleproduct singleproduct){
		//读取历史记录
		SingleproductHistory singleHis = singleHisService.querySingleHistoryBySingleId(singleproduct.getSingleId());
		if(singleHis == null)
			return;
		
		Singleproduct singleOld = this.changeToSingle(singleHis); 
		
		//检验系列信息
		Series Series = seriesService.querySeriesById(Integer.valueOf(singleOld.getOfOrigin()));
		if(Series == null)
			throw new BusinessException("1", "系列信息不存在");
		if(!"1".equals(Series.getIsUse()))
			throw new BusinessException("1", "系列已失效");
		
		//校验唯一性
		List<Singleproduct> list = singleproductDao.querySingleproductsAccurateForName(singleOld);
		if(list != null && list.size() >= 1)
			throw new BusinessException("1", "要恢复的单品信息和当前信息重复,历史信息["+singleOld.getSingleName()+"]["+singleOld.getSingleEname()+"]["+singleOld.getCapacity()+"]");
	
		//修改单品信息
		singleproductDao.updateSingleproduct(singleOld);
		
		//删除单品历史记录
		singleHisService.deleteSingleproductHistory(singleHis);
	}
	
	/**
	 * 同步单品信息到所有清单表（下单/邮寄/入库/售出）
	 * @param singleproduct
	 * @return
	 */
	public Map<String, Integer> synchronizationSingleToOrder(Singleproduct singleproduct){
		//检验单品信息合法性。 是否生效，系列是否存在，系列是否生效
		Singleproduct dbSingle = singleproductDao.querySingleproductBySingleId(singleproduct.getSingleId());
		
		if(dbSingle == null){
			throw new BusinessException("1", "单品信息不存在");
		}
		if(!"1".equals(dbSingle.getIsUse())){//未生效
			throw new BusinessException("1", "该单品尚未生效");
		}
		
		Series dbSeries = seriesService.querySeriesById(Integer.valueOf(dbSingle.getOfOrigin()));
		if(dbSeries == null)
			throw new BusinessException("1", "单品所属系列信息不存在");
		if(!"1".equals(dbSeries.getIsUse()))
			throw new BusinessException("1", "单品所属系列信息未生效");
		
		//开始同步
		Map<String, Integer> map = syncDirService.addSync(dbSingle);
		
		return map;
	}
	
	/**
	 * 恢复同步记录
	 * @param syncFlag
	 * @return
	 */
	public Map<String, Integer> recoverSyncSingle(Singleproduct single){
		//检验单品信息合法性。 是否生效，系列是否存在，系列是否生效
		Singleproduct dbSingle = singleproductDao.querySingleproductBySingleId(single.getSingleId());
		
		if(dbSingle == null){
			throw new BusinessException("1", "单品信息不存在");
		}
		if(!"1".equals(dbSingle.getIsUse())){//未生效
			throw new BusinessException("1", "该单品尚未生效");
		}
		
		Series dbSeries = seriesService.querySeriesById(Integer.valueOf(dbSingle.getOfOrigin()));
		if(dbSeries == null)
			throw new BusinessException("1", "单品所属系列信息不存在");
		if(!"1".equals(dbSeries.getIsUse()))
			throw new BusinessException("1", "单品所属系列信息未生效");
		
		//恢复记录开始
		Map<String, Integer> map = syncDirService.recoverSync(dbSingle);
		return map;
	}
	
	/**
	 * 添加或更新 单品历史信息
	 * @param singleNew
	 */
	private void addOrUpdateSingleproductHistoryInfo(Singleproduct singleNew){
		Integer singleId = singleNew.getSingleId();
		Singleproduct singleOld = singleproductDao.querySingleproductBySingleId(singleId);
		if(singleOld == null)
			throw new BusinessException("1", "该单品不存在");
		
		//查看单品历史信息是否存在
		SingleproductHistory singleHis = singleHisService.querySingleHistoryBySingleId(singleId);
		if(singleHis == null){//无历史记录
			singleHisService.addSingleproductHistory(singleOld);
		} else {
			SingleproductHistory singleHisNew = this.changeToSingleHis(singleOld);
			singleHisNew.setSingleHisId(singleHis.getSingleHisId());
			singleHisService.updateSingleproductHistory(singleHisNew);
		}
		
	}
	
	/**
	 * 单品模型转换为单品历史模型
	 * @param single
	 * @return
	 */
	private SingleproductHistory changeToSingleHis(Singleproduct single){
		SingleproductHistory singleHis = new SingleproductHistory();
		singleHis.setSingleId(single.getSingleId());
		singleHis.setSingleName(single.getSingleName());
		singleHis.setSingleEname(single.getSingleEname());
		singleHis.setIsUse(single.getIsUse());
		singleHis.setOfOrigin(single.getOfOrigin());
		singleHis.setCapacity(single.getCapacity());
		singleHis.setUnit(single.getUnit());
		return singleHis;
	}
	
	/**
	 * 单品历史模型转换为单品模型
	 * @param singleHis
	 * @return
	 */
	private Singleproduct changeToSingle(SingleproductHistory singleHis){
		Singleproduct single = new Singleproduct();
		single.setSingleId(singleHis.getSingleId());
		single.setSingleName(singleHis.getSingleName());
		single.setSingleEname(singleHis.getSingleEname());
		single.setIsUse(singleHis.getIsUse());
		single.setCapacity(singleHis.getCapacity());
		single.setOfOrigin(singleHis.getOfOrigin());
		single.setUnit(singleHis.getUnit());
		return single;
	}
}
