package com.cat.manage.shipped.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.cat.manage.order.domain.SubOrder;
import com.cat.manage.shipped.domain.ShippedHead;
import com.cat.manage.shipped.service.ShippedHeadService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 邮寄清单（主单）控制器
 * @author 王航
 * @date 2016年2月19日 下午5:02:40
 */
@RestController
@RequestMapping("/shippedHead")
public class ShippedHeadController {
	@Autowired
	private ShippedHeadService shippedHeadService;
	
	/**
	 * 该方法主要用于将下单清单中的记录添加到邮寄清单中
	 * 其中接收邮寄清单（主单）数据和下单清单唯一编号的数组
	 * 在服务方法中将下单清单记录查询出来整理后添加至邮寄清单
	 * @return
	 */
	@RequestMapping("/add")
	public Srm addShippedHeadAndShipped(ShippedHead shippedHead, Integer[] checkIds){
		shippedHeadService.addShippedHeadAndShipped(shippedHead, checkIds);
		return new Srm().setResultCode("0").setResultMessage("添加邮寄清单成功");
	}
	
	/**
	 * 修改邮寄清单主单
	 * @param shippedHead
	 * @return
	 */
	@RequestMapping("/modify")
	public Srm modifyShippedHead(ShippedHead shippedHead){
		shippedHeadService.updateShippedHead(shippedHead);
		return new Srm().setResultCode("0").setResultMessage("修改邮寄清单成功");
	}
	
	/**
	 * 根据邮寄清单主单唯一编号删除记录
	 * 同时删除所有子单记录
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	public Srm deleteShippedHead(Integer id){
		shippedHeadService.deleteShippedHead(id);
		return new Srm().setResultCode("0").setResultMessage("删除邮寄清单成功");
	}
	
	/**
	 * 查询主单信息（返回所有记录）
	 * @param shippedHead
	 * @return
	 */
	@RequestMapping("/queryAll")
	public Srm queryShippedHeadForList(ShippedHead shippedHead){
		List<ShippedHead> list = shippedHeadService.queryShippedHeadAll(shippedHead);
		return new Srm().setResultCode("0").setResultMessage("查询邮寄清单主单成功").addAll(list);
	}
	
	/**
	 * 查询邮寄清单主单（分页查询）
	 * @param shippedHead
	 * @param request
	 * @return
	 */
	@RequestMapping("/query")
	public Srm queryShippedHeadForPage(ShippedHead shippedHead, HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		Integer pageNum = params.getInt("page");
		Integer pageSize = params.getInt("limit");
		
		PageInfo<ShippedHead> page = shippedHeadService.queryShippedHead(shippedHead, pageNum, pageSize);
		return new Srm().setResultCode("0").setResultMessage("查询邮寄清单主单成功").buildPageInfo(page);
	}
}
