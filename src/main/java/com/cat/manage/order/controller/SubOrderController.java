package com.cat.manage.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.common.model.Srm;
import com.cat.manage.order.domain.Order;
import com.cat.manage.order.domain.SubOrder;
import com.cat.manage.order.service.SubOrderService;


@RestController
@RequestMapping("/subOrder")
public class SubOrderController {
	
	@Autowired
	private SubOrderService subOrderService;
	
	@RequestMapping("/add")
	public Srm addSubOrder(SubOrder subOrder){
		subOrderService.addSubOrder(subOrder);
		return new Srm().setResultCode("0").setResultMessage("添加子订单成功");
	}
	
	@RequestMapping("/queryById")
	public Srm querySubOrderById(Integer subOrderId){
		SubOrder subOrder = subOrderService.querySubOrder(subOrderId);
		return new Srm().setResultCode("0").setResultMessage("查询子订单成功").addResult(subOrder);
	}
	
	@RequestMapping("/queryAll")
	public Srm querySubOrderAll(String startTime, String endTime){
		List<SubOrder> list = subOrderService.querySubOrderAll(startTime, endTime);
		return new Srm().setResultCode("0").setResultCode("查询子订单成功").addAll(list);
	}
	
	@RequestMapping("/modify")
	public Srm modifySubOrder(SubOrder subOrder){
		subOrderService.updateSubOrder(subOrder);
		return new Srm().setResultCode("0").setResultMessage("修改子订单成功");
	}
	
	@RequestMapping("/group")
	public Srm modifySubOrderForGroup(Integer subOrderId, Integer num){
		subOrderService.updateSubOrderForGroup(subOrderId, num);
		return new Srm().setResultCode("0").setResultMessage("拆分子订单成功");
	}
}
