package com.cat.manage.selled.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.cat.manage.selled.domain.Selled;
import com.cat.manage.selled.service.SelledService;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @Description: 售出控制器
 * @author 王航
 * @date 2016年2月23日 下午3:41:29
 */
@RestController
@RequestMapping("/selled")
public class SelledController {

	@Autowired
	private SelledService selledService;
	
	@RequestMapping("/add")
	public Srm addSelled(Selled selled){
		if(selled.getStoreId() == null)
			throw new BusinessException("1", "仓库记录编号不能为空");
		if(selled.getSellingPrice() == null)
			throw new BusinessException("1", "售价不能为空");
		
		selledService.addSelled(selled);
		return new Srm().setResultCode("0").setResultMessage("添加售出清单成功");
	}
	
	@RequestMapping("/query")
	public Srm querySelledForPage(Selled selled, String startTime, String endTime, HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		Integer pageNum = params.getInt("page");
		Integer pageSize = params.getInt("limit");
		
		PageInfo<Selled> page = selledService.querySelledForPage(selled, startTime, endTime, pageNum, pageSize);
		return new Srm().setResultCode("0").setResultMessage("查询售出清单成功").buildPageInfo(page);
	}
	
	@RequestMapping("/modify")
	public Srm modifySelled(Selled selled){
		selledService.updateSelled(selled);
		return new Srm().setResultCode("0").setResultMessage("修改售出记录成功");
	}
	
	@RequestMapping("/delete")
	public Srm deleteSelled(Integer id){
		selledService.deleteSelled(id);
		return new Srm().setResultCode("0").setResultMessage("删除售出记录成功");
	}
	
	@RequestMapping("/refund")
	public Srm modifySelledForRefund(Selled selled){
		selledService.updateSelledForRefund(selled);
		return new Srm().setResultCode("0").setResultMessage("添加补损金额成功");
	}
	
	@RequestMapping("/destroy")
	public Srm addDestroy(Selled selled){
		selledService.addStoreForDestroy(selled);
		return new Srm().setResultCode("0").setResultMessage("添加损坏记录成功");
	}
	
	@RequestMapping("/lastPrice")
	public Srm querySingleLastSellPrice(Selled selled){
		if(selled == null || selled.getSingleId() == null)
			return new Srm().setResultCode("1").setResultMessage("参数为空");
		Selled s = selledService.querySingleLastSellPrice(selled.getSingleId());
		
		List<Map<String, String>> list = Lists.newArrayList();
		Map<String, String> map = Maps.newHashMap();
		
		if(s != null){
			map.put("lastPrice", String.format("%.02f", s.getSellingPrice()));
		} else {
			map.put("lastPrice", "0.00");
		}
		
		list.add(map);
		return new Srm().setResultCode("0").setResultMessage("查询售出最新价格成功").addAll(list);
	}
	
	@RequestMapping("/RecommendPrice")
	public Srm querySingleRecommendSellPrice(Selled selled){
		if(selled == null || selled.getSingleId() == null)
			return new Srm().setResultCode("1").setResultMessage("参数为空");
		if(selled == null || selled.getStoreId() == null)
			return new Srm().setResultCode("1").setResultMessage("参数为空");
		
		String recommendPrice = selledService.queryRecommendPrice(selled.getStoreId(), selled.getSingleId());
		
		List<Map<String, String>> list = Lists.newArrayList();
		Map<String, String> map = Maps.newHashMap();
		map.put("lastPrice", recommendPrice);
		
		list.add(map);
		return new Srm().setResultCode("0").setResultMessage("查询推荐售价成功").addAll(list);
	}
}
