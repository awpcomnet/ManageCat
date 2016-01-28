package com.cat.manage.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.cat.manage.order.domain.SubOrder;
import com.cat.manage.order.service.SubOrderService;
import com.github.pagehelper.PageInfo;


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
	
	@RequestMapping("/queryMore")
	public Srm querySubOrderAll(SubOrder subOrder, String startTime, String endTime, HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		Integer pageNum = params.getInt("page");
		Integer pageSize = params.getInt("limit");
		
		PageInfo<SubOrder> page = subOrderService.querySubOrderAllForName(subOrder, startTime, endTime, pageNum, pageSize);
		return new Srm().setResultCode("0").setResultMessage("查询子订单成功").buildPageInfo(page);
	}
	
	@RequestMapping("/modify")
	public Srm modifySubOrder(SubOrder subOrder){
		subOrderService.updateSubOrder(subOrder);
		return new Srm().setResultCode("0").setResultMessage("修改子订单成功");
	}
	
	@RequestMapping("/split")
	public Srm modifySubOrderForGroup(Integer subOrderId, Integer num){
		subOrderService.updateSubOrderForGroup(subOrderId, num);
		return new Srm().setResultCode("0").setResultMessage("拆分子订单成功");
	}
	
	@RequestMapping("/merge")
	public Srm mergeSubOrder(Integer[] subOrderIds, HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		Double orderPrice = params.getDouble("orderPrice");
		Double transferPrice = params.getDouble("transferPrice");
		Double costPrice = params.getDouble("costPrice");
		Double sellingPrice = params.getDouble("sellingPrice");
		String curState = params.getStr("curState");
		
		subOrderService.mergeSubOrder(subOrderIds, orderPrice, transferPrice, costPrice, sellingPrice, curState);
		return new Srm().setResultCode("0").setResultMessage("订单合并成功");
	}
	
	@RequestMapping("/delete")
	public Srm deleteSubOrder(Integer[] subOrderIds){
		if(subOrderIds == null || subOrderIds.length <= 0)
			return new Srm().setResultCode("1").setResultMessage("子订单删除失败，原因参数异常");
		subOrderService.deleteSubOrderByIds(subOrderIds);
		return new Srm().setResultCode("0").setResultMessage("子订单删除成功");
	}
}
