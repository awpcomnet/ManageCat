package com.cat.manage.store.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.check.service.CheckService;
import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.shipped.domain.Shipped;
import com.cat.manage.shipped.domain.ShippedHead;
import com.cat.manage.shipped.service.ShippedHeadService;
import com.cat.manage.shipped.service.ShippedService;
import com.cat.manage.store.dao.StoreDao;
import com.cat.manage.store.domain.Store;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 仓库服务类
 * @author wanghang
 *
 */
@Service
public class StoreService {

	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private ShippedService shippedService;
	
	@Autowired
	private ShippedHeadService shippedHeadService;
	
	@Autowired
	private CheckService checkService;
	
	/**
	 * 添加仓库信息
	 * store中必须存在shippedId邮寄清单子单唯一编号
	 * @param store 
	 */
	public void addStore(Store store){
		//验证邮寄清单子单是否存在
		Integer shippedId = store.getShippedId();
		
		if(shippedId == null)
			throw new BusinessException("1", "邮寄清单不存在");
		
		Shipped shipped = shippedService.queryShippedById(shippedId);
		if(shipped == null)
			throw new BusinessException("1", "邮寄清单不存在");
		
		if(!"1".equals(shipped.getShippedStatus()))
			throw new BusinessException("1", "邮寄清单状态不为[已邮寄]");
		
		//设置下单清单状态
		Integer checkId = shipped.getCheckId();
		checkService.updateCheckForStatus(new Integer[]{checkId}, "2");//已入库
		
		//设置邮寄清单状态
		shippedService.updateShippedForStatus(new Integer[]{shippedId}, "2");//已入库
		
		//设置入库状态
		store.setStoreStatus("2");//已入库
		
		//入库
		storeDao.addStore(shipped, store);
	}
	
	/**
	 * 分页查询入库清单信息
	 * @param store
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Store> queryStoreForPage(Store store, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Store> list = storeDao.queryStore(store);
		PageInfo<Store> page = new PageInfo<Store>(list);
		return page;
	}
	
	/**
	 * 根据邮寄清单唯一编号，计算该清单的邮费
	 * 1.查询主邮寄清单总邮费
	 * 2.统计该主单下所有子单[已邮寄]状态，数量和重量
	 * 3.统计该主单下所有子单[已入库]状态的实际邮费
	 * 4.计算公式  (总邮费-[已入库]实际邮费)*比例  
	 *         比例 = 重量/ ( ∑数量 * 重量 )
	 * @param shippedId
	 * @return
	 */
	public Map calculatePost(Integer shippedId){
		//查询邮寄清单
		Shipped shipped = shippedService.queryShippedById(shippedId);
		if(shipped == null)
			throw new BusinessException("1", "邮寄清单不存在");
		
		//暂存该邮寄清单重量和数量
		String weight = shipped.getWeight();
		Integer num = shipped.getNum();
		
		Integer headId = shipped.getHeadId();//主单唯一编号
		//获取主单信息和所有子单信息
		ShippedHead shippedHead = shippedHeadService.queryShippedHeadById(headId);
		if(shippedHead == null)
			throw new BusinessException("1", "邮寄清单主单不存在");
		Double HeadTotalPost = shippedHead.getPostage();
		List<Shipped> shippedList = shippedService.queryShippedByHeadId(headId);
		if(shippedList == null)
			throw new BusinessException("1", "邮寄清单子单信息不存在");
		
		Double weightTotal = 0.0;
		Double postTotal = 0.0;
		//计算[已邮寄]清单总重  计算[已入库]清单实际邮费（总）
		for(Shipped sh : shippedList){
			if("1".equals(sh.getShippedStatus())){//已邮寄
				Integer shNum = sh.getNum();
				String shWeight = sh.getWeight();
				weightTotal += shNum*Double.parseDouble(shWeight);
			}
			if("2".equals(sh.getShippedStatus())){//已入库
				Store store = storeDao.queryStoreByShippedId(sh.getId());
				postTotal += Double.parseDouble(store.getUnitPostage());
			}
		}
		//计算比例值
		Double scale = Double.parseDouble(weight)/weightTotal;
		//计算的邮费
		Double calculatePost = (HeadTotalPost - postTotal) * scale;
		
		return null;
	}
}
