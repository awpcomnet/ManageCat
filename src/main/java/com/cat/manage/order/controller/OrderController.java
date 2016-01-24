package com.cat.manage.order.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.cat.manage.order.domain.Order;
import com.cat.manage.order.service.OrderService;
import com.github.pagehelper.PageInfo;


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
	public Srm queryOrderAll(Order order, String startTime, String endTime, HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		Integer pageNum = params.getInt("page");
		Integer pageSize = params.getInt("limit");
		
		PageInfo<Order> page = orderService.queryOrderAll(order, startTime, endTime, pageNum, pageSize);
		return new Srm().setResultCode("0").setResultMessage("查询订单成功").buildPageInfo(page);
	}
	
	@RequestMapping("/modify")
	public Srm modifyOrder(Order order){
		orderService.updateOrder(order);
		return new Srm().setResultCode("0").setResultMessage("修改订单成功");
	}
	
	@RequestMapping("/delete")
	public Srm deleteOrders(Integer[] orderIds){
		if(orderIds == null || orderIds.length <= 0)
			return new Srm().setResultCode("1").setResultMessage("传入参数有误!");
		orderService.deleteOrder(orderIds);
		return new Srm().setResultCode("0").setResultMessage("删除主订单/子订单成功");
	}
	
	@RequestMapping("/confirm")
	public Srm confirmOrder(Integer[] orderIds){
		if(orderIds == null || orderIds.length <= 0)
			return new Srm().setResultCode("1").setResultMessage("传入参数有误!");
		orderService.updateOrderAndSub(orderIds);
		return new Srm().setResultCode("0").setResultMessage("一键确认主订单成功");
	}
}
