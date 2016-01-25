package com.cat.manage.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.order.dao.OrderDao;
import com.cat.manage.order.dao.SubOrderDao;
import com.cat.manage.order.domain.Order;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;

@Service
public class OrderService {
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private SubOrderService subOrderService;
	
	/**
	 * 添加订单
	 * @param order
	 */
	public void addOrder(Order order){
		orderDao.addOrder(order);
	}
	
	/**
	 * 查询单个订单
	 * @param orderId
	 * @return
	 */
	public Order queryOrderById(Integer orderId){
		return orderDao.queryOrderById(orderId);
	}
	
	/**
	 * 查询所有订单
	 * @return
	 */
	public List<Order> queryOrderAll(Order order, String startTime, String endTime){
		return orderDao.queryOrderAll(order, startTime, endTime);
	}
	
	/**
	 * 查询所有订单分页
	 * @return
	 */
	public PageInfo<Order> queryOrderAll(Order order, String startTime, String endTime, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Order> list = orderDao.queryOrderAll(order, startTime, endTime);
		PageInfo<Order> page = new PageInfo<Order>(list);
		return page;
	}
	
	/**
	 * 修改订单信息
	 * @param order
	 */
	public void updateOrder(Order order){
		orderDao.updateOrder(order);
		String subOrderState = this.getSubOrderState(order);
		Integer orderId = order.getOrderId();
		if(!Strings.isNullOrEmpty(subOrderState) && orderId != null){
			subOrderService.updateSubOrderForStatus(orderId, subOrderState);
		}
	}
	
	/**
	 * 更具主订单ID删除主订单,及其子订单
	 * @param orderId
	 */
	public void deleteOrder(Integer[] orderIds){
		for(Integer orderId : orderIds){
			orderDao.deleteOrder(orderId);
			subOrderService.deleteSubOrderByParentId(orderId);
		}
	}
	
	/**
	 * 确认主订单和子订单状态
	 * @param orderIds
	 */
	public void updateOrderAndSub(Integer[] orderIds){
		for(Integer orderId : orderIds){
			orderDao.updateOrderForstatus(orderId, "9", "9", "9");
			subOrderService.updateSubOrderForStatus(orderId, "9");
		}
	}
	
	/**
	 * 主订单状态翻译方法，根据主订单状态返回子订单当前状态
	 * @return
	 */
	private String getSubOrderState(Order order){
		String foreignState = order.getForeignState();//国外订单状态
		String transfer = order.getTransfer();//转运状态
		String affirmState = order.getAffirmState();//确认收货状态
		
		String subOrderState = "";
		
		if(affirmState.equals("9")){
			subOrderState =  "9";
		}else if(transfer.equals("9")){
			switch(Integer.valueOf(affirmState)){
				case 0:
					subOrderState = "4";
					break;
				case 1:
					subOrderState = "4";
					break; 
				case 2:
					subOrderState = "5";
					break;
				case 8:
					subOrderState = "8";
					break;
			}
		}else if(foreignState.equals("9")){
			switch(Integer.valueOf(transfer)){
				case 0:
					subOrderState = "1";
					break;
				case 1:
					subOrderState = "2";
					break; 
				case 2:
					subOrderState = "3";
					break;
				case 8:
					subOrderState = "8";
					break;
			}
		}
		
		switch(Integer.valueOf(foreignState)){
			case 0:
				subOrderState = "0";
				break;
			case 1:
				subOrderState = "1";
				break; 
			case 8:
				subOrderState = "8";
				break;
		}
		
		return subOrderState;
	}
}
