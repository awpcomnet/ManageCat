package com.cat.manage.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cat.manage.order.domain.Order;

@Repository
public interface OrderDao {
	
	/**
	 * 添加订单
	 * @param order
	 */
	public void addOrder(Order order);
	
	/**
	 * 根据ID查询订单
	 * @param orderId
	 * @return
	 */
	public Order queryOrderById(Integer orderId);
	
	/**
	 * 查询所有订单
	 * @return
	 */
	public List<Order> queryOrderAll(@Param("startTime") String startTime, @Param("endTime") String endTime);
	
	/**
	 * 修改订单
	 * @param order
	 */
	public void updateOrder(Order order);
}
