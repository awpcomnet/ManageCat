package com.cat.manage.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.order.dao.OrderDao;
import com.cat.manage.order.domain.Order;

@Service
public class OrderService {
	
	@Autowired
	private OrderDao orderDao;
	
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
	public List<Order> queryOrderAll(String startTime, String endTime){
		return orderDao.queryOrderAll(startTime, endTime);
	}
	
	/**
	 * 修改订单信息
	 * @param order
	 */
	public void updateOrder(Order order){
		orderDao.updateOrder(order);
	}
}
