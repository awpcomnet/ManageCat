package com.cat.manage.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.common.model.Srm;
import com.cat.manage.order.domain.Order;
import com.cat.manage.order.service.OrderService;


@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/add")
	public Srm addOrder(Order order){
		orderService.addOrder(order);
		return new Srm().setResultCode("0").setResultMessage("添加订单成功");
	}
	
	@RequestMapping("/queryById")
	public Srm queryOrderById(Integer orderId){
		Order order = orderService.queryOrderById(orderId);
		return new Srm().setResultCode("0").setResultMessage("查询订单成功").addResult(order);
	}
	
	@RequestMapping("/queryAll")
	public Srm queryOrderAll(String startTime, String endTime){
		List<Order> list = orderService.queryOrderAll(startTime, endTime);
		return new Srm().setResultCode("0").setResultCode("查询订单成功").addAll(list);
	}
	
	@RequestMapping("/modify")
	public Srm modifyOrder(Order order){
		orderService.updateOrder(order);
		return new Srm().setResultCode("0").setResultMessage("修改订单成功");
	}
}
