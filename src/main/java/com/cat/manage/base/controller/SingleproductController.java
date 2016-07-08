package com.cat.manage.base.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.base.domain.Series;
import com.cat.manage.base.domain.Singleproduct;
import com.cat.manage.base.service.SingleproductService;
import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/singleproduct")
public class SingleproductController {
	@Autowired
	private SingleproductService singleproductService;
	
	@RequestMapping("/add")
	public Srm addSingleproduct(Singleproduct singleproduct){
		//参数处理(去除空格)
		singleproduct.setSingleName(singleproduct.getSingleName().trim());
		singleproduct.setSingleEname(singleproduct.getSingleEname().trim());
		singleproduct.setCapacity(singleproduct.getCapacity().trim());
		
		boolean isAdd = singleproductService.addSingleproduct(singleproduct);
		if(isAdd)
			return new Srm().setResultCode("0").setResultMessage("添加单品信息成功");
		else
			return new Srm().setResultCode("1").setResultMessage("该单品已存在，校验单品名称["+singleproduct.getSingleName()+"]，英文名称["+singleproduct.getSingleEname()+"]，规格["+singleproduct.getCapacity()+"]");
	}
	
	@RequestMapping("/update")
	public Srm updateSingleproduct(Singleproduct singleproduct){
		//参数处理(去除空格)
		singleproduct.setSingleName(singleproduct.getSingleName().trim());
		singleproduct.setSingleEname(singleproduct.getSingleEname().trim());
		singleproduct.setCapacity(singleproduct.getCapacity().trim());
		
		boolean isModify = singleproductService.updateSingleproduct(singleproduct);
		if(isModify)
			return new Srm().setResultCode("0").setResultMessage("修改单品信息成功");
		else
			return new Srm().setResultCode("1").setResultMessage("该单品已存在，校验单品名称["+singleproduct.getSingleName()+"]，英文名称["+singleproduct.getSingleEname()+"]，规格["+singleproduct.getCapacity()+"]");
	}
	
	@RequestMapping("/delete")
	public Srm deleteSingleproduct(Integer singleId){
		singleproductService.deleteSingleproduct(singleId);
		return new Srm().setResultCode("0").setResultMessage("删除单品信息成功");
	}
	
	@RequestMapping("/query")
	public Srm querySingleproduct(Singleproduct singleproduct, HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		Integer pageNum = params.getInt("page");
		Integer pageSize = params.getInt("limit");
		
		//单品查询添加对某品牌的所有单品的查询
		String brandId = params.getStrIgnoreNull("brandId");
		
		PageInfo<Singleproduct> page = singleproductService.querySingleproduct(singleproduct, pageNum, pageSize, brandId);
		return new Srm().setResultCode("0").setResultMessage("查询单品信息成功").buildPageInfo(page);
	}
	
	@RequestMapping("/queryAll")
	public Srm querySingleproductAll(Singleproduct singleproduct, HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		
		//单品查询添加对某品牌的所有单品的查询
		String brandId = params.getStrIgnoreNull("brandId");
		List<Singleproduct> list = singleproductService.querySingleproductAll(singleproduct, brandId);
		return new Srm().setResultCode("0").setResultMessage("查询所有单品信息成功").addAll(list);
	}
	
	@RequestMapping("/recover")
	public Srm recoverSingleproduct(Singleproduct singleproduct){
		singleproductService.recoverSingleproduct(singleproduct);
		return new Srm().setResultCode("0").setResultMessage("恢复单品历史信息成功");
	}
	
	@RequestMapping("/sync")
	public Srm syncSingleproduct(Singleproduct single){
		Map<String, Integer> map = singleproductService.synchronizationSingleToOrder(single);
		String tip = "同步单品完成,本次同步影响[下单清单 "+map.get("check")+" 条]，[邮寄清单 "+map.get("shipped")+"  条]，"
				+ "[入库清单 "+map.get("store")+"  条]，[售出清单 "+map.get("selled")+"  条]";
		return new Srm().setResultCode("0").setResultMessage(tip);
	}
	
	@RequestMapping("/recoverSync")
	public Srm recoverSyncSingleproduct(Singleproduct single){
		Map<String, Integer> map = singleproductService.recoverSyncSingle(single);
		String tip = "恢复单品完成,本次恢复影响[下单清单 "+map.get("check")+" 条]，[邮寄清单 "+map.get("shipped")+"  条]，"
				+ "[入库清单 "+map.get("store")+"  条]，[售出清单 "+map.get("selled")+"  条]";
		return new Srm().setResultCode("0").setResultMessage(tip);
	}
}
