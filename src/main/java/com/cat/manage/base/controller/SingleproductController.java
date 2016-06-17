package com.cat.manage.base.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		singleproductService.addSingleproduct(singleproduct);
		return new Srm().setResultCode("0").setResultMessage("添加单品信息成功");
	}
	
	@RequestMapping("/update")
	public Srm updateSingleproduct(Singleproduct singleproduct){
		singleproductService.updateSingleproduct(singleproduct);
		return new Srm().setResultCode("0").setResultMessage("修改单品信息成功");
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
}
