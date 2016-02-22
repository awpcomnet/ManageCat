package com.cat.manage.shipped.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.cat.manage.shipped.domain.Shipped;
import com.cat.manage.shipped.service.ShippedService;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

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
	
	@RequestMapping("/weight")
	public Srm modifyShippedWeight(String[] params){
		//参数处理
		List<Shipped> list = Lists.newArrayList();
		for(String param : params){
			String[] idAndWeight = param.split("\\|");
			if(idAndWeight.length != 2)
				continue;
			Shipped shipped = new Shipped();
			shipped.setId(Integer.valueOf(idAndWeight[0].trim()));
			shipped.setWeight(idAndWeight[1].trim());
			list.add(shipped);
		}
		
		shippedService.updateShippedForWeight(list);
		return new Srm().setResultCode("0").setResultMessage("修改邮寄清单重量成功");
	}
}
