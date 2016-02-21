package com.cat.manage.shipped.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.cat.manage.shipped.domain.Shipped;
import com.cat.manage.shipped.service.ShippedService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 邮寄清单（子单）控制器
 * @author 王航
 * @date 2016年2月19日 下午5:01:49
 */
@RestController
@RequestMapping("/shipped")
public class ShippedController {
	@Autowired
	private ShippedService shippedService;
	
	@RequestMapping("/query")
	public Srm queryShipped(Shipped shipped, HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		Integer pageNum = params.getInt("page");
		Integer pageSize = params.getInt("limit");
		
		PageInfo<Shipped> page = shippedService.queryShipped(shipped, pageNum, pageSize);
		return new Srm().setResultCode("0").setResultMessage("查询邮寄清单子单成功").buildPageInfo(page);
	}
	
	@RequestMapping("/modify")
	public Srm modifyShipped(Shipped shipped){
		shippedService.updateShipped(shipped);
		return new Srm().setResultCode("0").setResultMessage("修改邮寄清单子单成功");
	}
	
	@RequestMapping("/delete")
	public Srm deleteShipped(Integer[] ids){
		shippedService.deleteShippedByIds(ids);
		return new Srm().setResultCode("0").setResultMessage("删除邮寄清单子单成功");
	}
}
