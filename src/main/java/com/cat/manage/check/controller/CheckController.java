package com.cat.manage.check.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.check.domain.Check;
import com.cat.manage.check.service.CheckService;
import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.cat.manage.order.domain.SubOrder;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/check")
public class CheckController {
	@Autowired
	private CheckService checkService;
	
	@RequestMapping("/add")
	public Srm addCheck(Check check){
		//添加下单清单是设置状态
		check.setOrderStatus("0");//已下单
		checkService.addCheck(check);
		return new Srm().setResultCode("0").setResultMessage("添加下单记录成功");
	}
	
	@RequestMapping("/modify")
	public Srm modifyCheck(Check check){
		//检查订单状态是否为 已下单
		if(!"0".equals(check.getOrderStatus())){
			return new Srm().setResultCode("1").setResultMessage("清单状态不为【已下单】");
		}
		checkService.updateCheck(check);
		return new Srm().setResultCode("0").setResultMessage("修改下单清单成功");
	}
	
	@RequestMapping("/delete")
	public Srm deleteCheck(Integer id){
		checkService.deleteCheck(id);
		return new Srm().setResultCode("0").setResultMessage("删除订单成功");
	}
	
	@RequestMapping("/query")
	public Srm queryCheck(Check check, String startTime, String endTime, HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		Integer pageNum = params.getInt("page");
		Integer pageSize = params.getInt("limit");
		
		PageInfo<Check> page = checkService.queryCheck(check, startTime, endTime, pageNum, pageSize);
		return new Srm().setResultCode("0").setResultMessage("查询订单成功").buildPageInfo(page);
	}
}
