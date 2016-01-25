package com.cat.manage.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.order.dao.SubOrderDao;
import com.cat.manage.order.domain.SubOrder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class SubOrderService {
	
	@Autowired
	private SubOrderDao subOrderDao;
	
	/**
	 * 添加子订单
	 * @param subOrder
	 */
	public void addSubOrder(SubOrder subOrder){
		subOrderDao.addSubOrder(subOrder);
	}
	
	/**
	 * 查询单个子订单
	 */
	public SubOrder querySubOrder(Integer subOrderId){
		return subOrderDao.querySubOrderById(subOrderId);
	}
	
	/**
	 * 查询所有子订单
	 * @return
	 */
	public List<SubOrder> querySubOrderAll(String startTime, String endTime){
		return subOrderDao.querySubOrderAll(startTime, endTime);
	}
	
	/**
	 * 查询所有子订单
	 * @return
	 */
	public PageInfo<SubOrder> querySubOrderAllForName(SubOrder subOrder, String startTime, String endTime, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<SubOrder> list = subOrderDao.querySubOrderAllForName(subOrder, startTime, endTime);
		PageInfo<SubOrder> page = new PageInfo<SubOrder>(list);
		return page;
	}
	
	/**
	 * 修改子订单信息
	 * @param subOrder
	 */
	public void updateSubOrder(SubOrder subOrder){
		subOrderDao.updateSubOrder(subOrder);
	}
	
	/**
	 * 子订单拆分
	 * @param subOrderId 原子订单Id
	 * @param num 原子订单拆分后剩余数量
	 */
	public void updateSubOrderForGroup(Integer subOrderId, int num){
		//查询原订单信息
		SubOrder subOrder = subOrderDao.querySubOrderById(subOrderId);
		if(subOrder == null)
			return;
		
		Integer sum = subOrder.getNum();
		//复制订单
		if(sum <= num)
			throw new BusinessException("1", "拆分子订单失败，拆分订单的产品数量大于或等于原有数量");
		Integer addFlag = subOrderDao.addSubOrderForCopy(subOrderId, sum-num);
		if(addFlag == 0)
			throw new BusinessException("1", "拆分子订单失败，未查询到要拆分的订单");
		//修改原订单
		SubOrder modifyOrder = new SubOrder();
		modifyOrder.setSuborderId(subOrderId);
		modifyOrder.setNum(num);
		Integer modifyFlag = subOrderDao.updateSubOrder(modifyOrder);
		if(modifyFlag == 0)
			throw new BusinessException("1", "拆分子订单失败，未查询到要拆分的订单");
	}
	
	/**
	 * 合并子订单
	 * @param subOrderIds 需要合并的所有子订单ID
	 * @param orderPrice 合并后下单单价
	 * @param transferPrice 合并后运费（单个）
	 * @param costPrice 合并后成本单价
	 * @param sellingPrice 合并后销售单价
	 * @param curState 合并后子订单状态
	 */
	public void mergeSubOrder(Integer[] subOrderIds, Double orderPrice, Double transferPrice, Double costPrice, Double sellingPrice, String curState){
		//TODO 合并子订单
	}
}
