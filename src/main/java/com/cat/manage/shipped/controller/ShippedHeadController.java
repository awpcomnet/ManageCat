package com.cat.manage.shipped.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.common.model.Srm;
import com.cat.manage.shipped.domain.ShippedHead;
import com.cat.manage.shipped.service.ShippedHeadService;

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
	public Srm addShippedHeadAndShipped(ShippedHead shippedHead, Integer[] checkIds){
		shippedHeadService.addShippedHeadAndShipped(shippedHead, checkIds);
		return new Srm().setResultCode("0").setResultMessage("添加邮寄清单成功");
	}
}
