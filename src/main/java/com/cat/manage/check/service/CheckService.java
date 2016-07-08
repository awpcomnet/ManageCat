package com.cat.manage.check.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.check.dao.CheckDao;
import com.cat.manage.check.domain.Check;
import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.selled.service.SelledService;
import com.cat.manage.shipped.service.ShippedService;
import com.cat.manage.store.service.StoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 下单清单服务
 * @author wanghang
 *
 */
@Service
public class CheckService {
	@Autowired
	private CheckDao checkDao;
	
	@Autowired
	private ShippedService shippedService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private SelledService selledService;
	
	/**
	 * 添加一条下单清单
	 */
	public void addCheck(Check check){
		checkDao.addCheck(check);
	}
	
	/**
	 * 修改一条下单清单
	 * @param check
	 */
	public void updateCheck(Check check){
		checkDao.updateCheck(check);
	}
	
	/**
	 * 根据下单清单唯一编号修改订单状态
	 * @param ids
	 * @param orderStatus
	 */
	public void updateCheckForStatus(Integer[] ids, String orderStatus){
		checkDao.updateCheckForStatus(ids, orderStatus);
	}
	
	/**
	 * 删除一条下单清单
	 * @param id
	 */
	public void  deleteCheck(Integer id){
		//删除下单清单记录
		checkDao.deleteCheck(id);
		
		//删除邮寄清单记录
		shippedService.deleteShippedByCheckId(id);
		
		//删除入库清单记录
		storeService.deleteStoreByCheckId(id);

		//删除售出清单记录
		selledService.deleteSelledByCheckId(id);
	}
	
	/**
	 * 分页查询下单清单记录
	 * @param check
	 * @param startTime
	 * @param endTime
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Check> queryCheck(Check check, String startTime, String endTime, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Check> list = checkDao.queryCheck(check, startTime, endTime);
		PageInfo<Check> page = new PageInfo<Check>(list);
		return page;
	}
	
	/**
	 * 
	 * @param check
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Check> queryCheckForList(Check check, String startTime, String endTime){
		return checkDao.queryCheck(check, startTime, endTime);
	}
	
	/**
	 * 根据下单清单唯一编号查询记录(多条)
	 * @param ids
	 * @return
	 */
	public List<Check> queryCheckByIds(Integer[] ids){
		return checkDao.queryCheckByIds(ids);
	}
	
	
	/**
	 * 根据下单清单唯一编号查询记录
	 * @param id
	 * @return
	 */
	public Check queryCheckById(Integer id){
		return checkDao.queryCheckById(id);
	}
	
	/**
	 * 查询下单清单，仅系列/单品 同步时使用
	 * @param seriesId
	 * @param singleId
	 * @return
	 */
	public List<Check> queryCheckForSync(Integer seriesId, Integer singleId){
		if(seriesId == null && singleId == null)
			return null;
		if(seriesId != null && singleId != null)
			return null;
		return checkDao.queryCheckForSync(seriesId, singleId);
	}
	
	/**
	 * 修改下单清单的 品牌，系列，单品， 仅系列/单品 同步时使用
	 * @param id
	 * @param brandId
	 * @param seriesId
	 * @param singleId
	 */
	public void updateCheckForSync(Integer id, Integer brandId, Integer seriesId, Integer singleId){
		Check check = new Check();
		check.setId(id);
		check.setBrandId(brandId);
		check.setSeriesId(seriesId);
		check.setSingleId(singleId);
		checkDao.updateCheck(check);
	}
}
