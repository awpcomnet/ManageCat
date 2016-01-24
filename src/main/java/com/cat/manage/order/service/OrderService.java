package com.cat.manage.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.order.dao.OrderDao;
import com.cat.manage.order.dao.SubOrderDao;
import com.cat.manage.order.domain.Order;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class OrderService {
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private SubOrderDao subOrderDao;
	
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
	}
	
	/**
	 * 更具主订单ID删除主订单,及其子订单
	 * @param orderId
	 */
	public void deleteOrder(Integer[] orderIds){
		for(Integer orderId : orderIds){
			orderDao.deleteOrder(orderId);
			subOrderDao.deleteSubOrderByParentId(orderId);
		}
	}
	
	/**
	 * 确认主订单和子订单状态
	 * @param orderIds
	 */
	public void updateOrderAndSub(Integer[] orderIds){
		for(Integer orderId : orderIds){
			orderDao.updateOrderForstatus(orderId, "9", "9", "9");
			subOrderDao.updateSubOrderForStatus(orderId, "5");
		}
	}
}
