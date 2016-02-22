package com.cat.manage.shipped.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.check.domain.Check;
import com.cat.manage.check.service.CheckService;
import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.shipped.dao.ShippedDao;
import com.cat.manage.shipped.domain.Shipped;
import com.cat.manage.shipped.domain.ShippedHead;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

/**
 * 
 * @Description: 邮寄清单（子单）服务
 * @author 王航
 * @date 2016年2月19日 下午4:45:38
 */
@Service
public class ShippedService {
	@Autowired
	private ShippedDao shippedDao;
	
	@Autowired
	private ShippedHeadService shippedHeadService;
	
	@Autowired
	private CheckService checkService;
	
	/**
	 * 添加邮寄清单（子单）
	 * @param shipped
	 */
	public void addShipped(ShippedHead shippedHead, Shipped shipped, Check check){
		shippedDao.addShipped(shippedHead, shipped, check);
	}
	
	/**
	 * 修改邮寄清单（子单）
	 * @param shipped
	 */
	public void updateShipped(Shipped shipped){
		shippedDao.updateShipped(shipped);
	}
	
	/**
	 * 单独用于修改邮寄清单重量
	 * @param shippedList
	 */
	public void updateShippedForWeight(List<Shipped> shippedList){
		if(shippedList == null || shippedList.size() <= 0)
			return;
		
		for(Shipped sh : shippedList){
			updateShipped(sh);
		}
	}
	
	/**
	 * 根据邮寄清单子单编号修改清单状态
	 * @param ids
	 * @param shippedStatus
	 */
	public void updateShippedForStatus(Integer[] ids, String shippedStatus){
		shippedDao.updateShippedForStatus(ids, shippedStatus);
	}
	
	/**
	 * 根据邮寄清单唯一编号删除记录
	 * @param id
	 */
	public void deleteShipped(Integer id){
		shippedDao.deleteShipped(id);
	}
	
	/**
	 * 根据下单清单唯一编号删除记录
	 * @param checkId
	 */
	public void deleteShippedByCheckId(Integer checkId){
		//查询子单信息
		Shipped shipped = new Shipped();
		shipped.setCheckId(checkId);
		List<Shipped> list = shippedDao.queryShipped(shipped);
		if(list == null)
			return;
		if(list.size() > 1)
			throw new BusinessException("1", "业务异常，下单清单编号["+checkId+"]存在"+list.size()+"个邮寄清单");
		
		//获取存储信息
		shipped = list.get(0);
		
		//获取主单唯一编号
		Integer headId = shipped.getHeadId();
		
		//删除子单
		shippedDao.deleteShippedByCheckId(checkId);
		
		//查询主单编号下是否还存在子单
		List<Shipped> shippedList = shippedDao.queryShippedByHeadId(headId);
		
		//不存在直接删除主单信息
		if(shippedList == null || shippedList.size() <= 0)
			shippedHeadService.deleteShippedHead(headId);
		
	}
	
	/**
	 * 根据邮寄清单主单唯一编号删除记录
	 * @param headId
	 */
	public void deleteShippedByHeadId(Integer headId){
		shippedDao.deleteShippedByHeadId(headId);
	}
	
	/**
	 * 根据邮寄清单子单删除记录
	 * 如果主单下已没有记录，同时删除主单
	 * @param ids
	 */
	public void deleteShippedByIds(Integer[] ids){
		//查询需要删除的订单记录
		List<Shipped> list = shippedDao.queryShippedByIds(ids);
		if(list == null || list.size() <= 0)
			throw new BusinessException("1", "未找到需要删除的订单记录。");
		
		//校验合法性,重新确定需要删除记录的ID
		List<Integer> newIds = Lists.newArrayList();
		List<Integer> checkIds = Lists.newArrayList();
		Integer headId = null;
		for(Shipped shipped : list){
			if(headId == null) {
				headId = shipped.getHeadId();
			} else {
				if(headId != shipped.getHeadId())
					throw new BusinessException("1", "要删除的邮寄清单记录非同一主邮寄清单内记录");
			}
			
			if(!"1".equals(shipped.getShippedStatus()))
				throw new BusinessException("1", "存在邮寄清单不为[已邮寄]状态");
			
			newIds.add(shipped.getId());
			checkIds.add(shipped.getCheckId());
		}
		
		ShippedHead shippedHead = shippedHeadService.queryShippedHeadById(headId);
		if(shippedHead == null)
			throw new BusinessException("1", "邮寄清单主单不存在");
		
		System.out.println("#######>");
		Integer[] aa = (Integer[])newIds.toArray(new Integer[]{});
		for(Integer a : aa){
			System.out.println(a);
		}
		
		//删除记录
		shippedDao.deleteShippedByIds((Integer[])newIds.toArray(new Integer[]{}));
		
		//恢复下单清单记录状态
		checkService.updateCheckForStatus((Integer[])checkIds.toArray(new Integer[]{}), "0");//恢复为[已下单状态]
		
		//查询剩余记录数
		List<Shipped> lastShippeds = shippedDao.queryShippedByHeadId(headId);
		if(lastShippeds == null || lastShippeds.size() <= 0)
			shippedHeadService.deleteShippedHeadById(headId);
			
	}
	
	/**
	 * 查询邮寄清单（子单）记录
	 * @param shipped
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Shipped> queryShipped(Shipped shipped, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Shipped> list = shippedDao.queryShipped(shipped);
		PageInfo<Shipped> page = new PageInfo<Shipped>(list);
		return page;
	}
	
	/**
	 * 根据邮寄清单主单唯一编号查询子单信息
	 * @param headId
	 * @return
	 */
	public List<Shipped> queryShippedByHeadId(Integer headId){
		return shippedDao.queryShippedByHeadId(headId);
	}
	
	/**
	 * 根据邮寄清单子单唯一编号查询记录
	 * @param id
	 * @return
	 */
	public Shipped queryShippedById(Integer id){
		return shippedDao.queryShippedById(id);
	}
	
}
