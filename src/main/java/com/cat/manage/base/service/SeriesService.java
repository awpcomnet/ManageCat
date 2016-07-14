package com.cat.manage.base.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.base.dao.SeriesDao;
import com.cat.manage.base.domain.Brand;
import com.cat.manage.base.domain.Series;
import com.cat.manage.base.domain.SeriesHistory;
import com.cat.manage.check.domain.Check;
import com.cat.manage.check.service.CheckService;
import com.cat.manage.common.exception.BusinessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * 系列服务
 * @author wanghang
 *
 */
@Service
public class SeriesService {
	@Autowired
	private SeriesDao seriesDao;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private SingleproductService singleService;
	
	@Autowired
	private CheckService checkService;
	
	@Autowired
	private SeriesHisService seriesHisService;
	
	@Autowired
	private SyncDirectoryService syncDirService;
	
	/**
	 * 添加系列信息
	 * @param series
	 */
	public boolean addSeries(Series series){
		Brand brand = brandService.queryBrandById(Integer.valueOf(series.getOfOrigin()));
		if(brand == null)
			throw new BusinessException("1", "品牌信息不存在");
		if(!"1".equals(brand.getIsUse()))
			throw new BusinessException("1", "品牌已失效");
		
		//检测品牌是否存在(中文名|英文名 任意一个重复则已存在)
		List<Series> list = seriesDao.querySeriesAccurateForName(series);
		if(list != null && list.size() >= 1)
			return false;
		
		
		
		seriesDao.addSeries(series);
		return true;
	}
	
	/**
	 * 修改系列信息
	 * @param series
	 */
	public boolean updateSeries(Series series){
		Brand brand = brandService.queryBrandById(Integer.valueOf(series.getOfOrigin()));
		if(brand == null)
			throw new BusinessException("1", "品牌信息不存在");
		if(!"1".equals(brand.getIsUse()))
			throw new BusinessException("1", "品牌已失效");
		
		//检测品牌是否存在(中文名|英文名 任意一个重复则已存在)
		List<Series> list = seriesDao.querySeriesAccurateForName(series);
		if(list != null && list.size() >= 1)
			return false;
		
		//保存历史信息
		this.addOrUpdateSeriesHistoryInfo(series);
		
		seriesDao.updateSeries(series);
		return true;
	}
	
	/**
	 * 根据唯一编号删除系列信息
	 * @param seriesId
	 */
	public void deleteSeries(Integer seriesId){
		//删除系列信息
		seriesDao.deleteSeries(seriesId);
		
		//删除单品信息
		singleService.deleteSingleproductBySeriesId(new Integer[]{seriesId});
		
		//删除系列历史信息
		seriesHisService.deleteSeriesHistoryInfo(seriesId);
	}
	
	/**
	 * 根据品牌编号删除系列
	 * @param brandId
	 */
	public void deleteSeriesByBrandId(Integer brandId){
		//查询系列
		List<Series> seriesList = this.querySeriesByBrandId(brandId);
		if(seriesList == null || seriesList.size() <= 0){
			return;
		}
		
		//记录系列编号
		List<Integer> seriesIds = Lists.newArrayList();
		for(Series series : seriesList){
			seriesIds.add(series.getSeriesId());
		}
		
		//删除系列信息
		seriesDao.deleteSeriesByBrandId(brandId);
		
		//删除系列历史信息
		for(Integer seriesId: seriesIds){
			seriesHisService.deleteSeriesHistoryInfo(seriesId);
		}
		
		//删除单品
		singleService.deleteSingleproductBySeriesId((Integer[])seriesIds.toArray(new Integer[]{}));
	}
	
	/**
	 * 查询系列信息
	 * @param series
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Series> querySeries(Series series, Integer pageNum, Integer pageSize, String orderBy){
		PageHelper.startPage(pageNum, pageSize);
		List<Series> list = seriesDao.querySeries(series, orderBy);
		PageInfo<Series> page = new PageInfo<Series>(list);
		return page;
	}
	
	/**
	 * 查询所有系列信息
	 * @param series
	 * @return
	 */
	public List<Series> querySeriesAll(Series series, String orderBy){
		return seriesDao.querySeries(series, orderBy);
	}
	
	/**
	 * 根据系列编号查询系列信息
	 * @param seriesId
	 * @return
	 */
	public Series querySeriesById(Integer seriesId){
		return seriesDao.querySeriesById(seriesId);
	}
	
	/**
	 * 根据品牌查询系列信息
	 * @param brandId
	 * @return
	 */
	public List<Series> querySeriesByBrandId(Integer brandId){
		Series series = new Series();
		series.setOfOrigin(brandId+"");
		
		return seriesDao.querySeries(series, null);
	}
	
	/**
	 * 查询下单清单中所有属于  该系列
	 * @param series
	 * @return
	 */
	public List<Check> queryAffectCheck(Series series){
		List<Check> list = Lists.newArrayList();
		Check check = new Check();
		//check.setBrandId(Integer.parseInt(series.getOfOrigin()));
		check.setSeriesId(series.getSeriesId());
		
		list = checkService.queryCheckForList(check, null, null);
		return list;
	}
	
	/**
	 * 恢复系列历史信息
	 * @param series
	 */
	public void recoverSeries(Series series){
		//查询历史信息表
		SeriesHistory seriesHistory = seriesHisService.querySeriesHisBySeriesId(series.getSeriesId());
		if(seriesHistory == null)
			return;
		
		Brand brand = brandService.queryBrandById(Integer.valueOf(seriesHistory.getOfOrigin()));
		if(brand == null)
			throw new BusinessException("1", "品牌信息不存在");
		if(!"1".equals(brand.getIsUse()))
			throw new BusinessException("1", "品牌已失效");
		
		//检测品牌是否存在(中文名|英文名 任意一个重复则已存在)
		Series stemp = this.hisChangeToSeries(seriesHistory);
		List<Series> list = seriesDao.querySeriesAccurateForName(stemp);
		if(list != null && list.size() >= 1)
			throw new BusinessException("1", "要恢复的系列信息和当前信息重复,历史信息["+seriesHistory.getSeriesName()+"]["+seriesHistory.getSeriesEname()+"]");
		
		//修改系列信息
		seriesDao.updateSeries(stemp);

		//删除历史信息
		seriesHisService.deleteSeriesHistoryInfo(stemp.getSeriesId());
		
	}
	
	/**
	 * 同步系列
	 * @param series
	 */
	public Map<String, Integer> syncSeries(Series series){
		Series dbSeries = seriesDao.querySeriesById(series.getSeriesId());
		if(dbSeries == null)
			throw new BusinessException("1", "要同步的系列不存在");
		if(!"1".equals(dbSeries.getIsUse()))
			throw new BusinessException("1", "该系列已经失效");
		
		//开始同步
		Map<String, Integer> map = syncDirService.addSync(dbSeries);
		return map;
	}
	
	/**
	 * 恢复系列同步
	 * @param syncFlag
	 * @return
	 */
	public Map<String, Integer> recoverSyncSeries(Series series){
		Series dbSeries = seriesDao.querySeriesById(series.getSeriesId());
		if(dbSeries == null)
			throw new BusinessException("1", "要同步的系列不存在");
		if(!"1".equals(dbSeries.getIsUse()))
			throw new BusinessException("1", "该系列已经失效");
		
		//开始恢复
		Map<String, Integer> map = syncDirService.recoverSync(dbSeries);
		return map;
	} 
	
	/**
	 * 添加或更新系列历史信息
	 * @param seriesId
	 */
	private void addOrUpdateSeriesHistoryInfo(Series seriesNew){
		Integer seriesId = seriesNew.getSeriesId();
		Series seriesOld = seriesDao.querySeriesById(seriesId);
		if(seriesOld == null)
			throw new BusinessException("1", "该系列不存在");
		
		//查询历史记录是否存在
		SeriesHistory seriesHistory = seriesHisService.querySeriesHisBySeriesId(seriesId);
		
		if(seriesHistory == null){
			if(seriesOld.getOfOrigin().equals(seriesNew.getOfOrigin())){//相同品牌
				seriesHisService.addSeriesHis(seriesOld, "0");
			} else {
				seriesHisService.addSeriesHis(seriesOld);
			}
		} else {
			if(!seriesOld.getOfOrigin().equals(seriesNew.getOfOrigin())){//不同品牌
				seriesHistory.setNeedSynchronization("1");
			}
			
			seriesHistory.setSeriesName(seriesOld.getSeriesName());
			seriesHistory.setSeriesEname(seriesOld.getSeriesEname());
			seriesHistory.setIsUse(seriesOld.getIsUse());
			seriesHistory.setOfOrigin(seriesOld.getOfOrigin());
			seriesHisService.updateSeriesHis(seriesHistory);
		}
	}
	
	/**
	 * 删除历史信息
	 * @param seriesId
	 */
//	private void deleteSeriesHistoryInfo(Integer seriesId){
//		//查询历史信息
//		SeriesHistory seriesHistory = seriesHisService.querySeriesHisBySeriesId(seriesId);
//		
//		if(seriesHistory.getSeriesHisId() != null)
//			seriesHisService.deleteSeriesHis(seriesHistory);
//	}
	
	/**
	 * 将系列历史信息转变为系列模型信息
	 * @param seriesHistory
	 * @return
	 */
	private Series hisChangeToSeries(SeriesHistory seriesHistory){
		Series series = new Series();
		series.setSeriesId(seriesHistory.getSeriesId());
		series.setSeriesName(seriesHistory.getSeriesName());
		series.setSeriesEname(seriesHistory.getSeriesEname());
		series.setIsUse(seriesHistory.getIsUse());
		series.setOfOrigin(seriesHistory.getOfOrigin());
		return series;
	}
}
