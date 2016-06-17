package com.cat.manage.base.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.base.domain.Brand;
import com.cat.manage.base.service.BrandService;
import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/brand")
public class BrandController {
	@Autowired
	private BrandService brandService;
	
	@RequestMapping("/add")
	public Srm addBrand(Brand brand){
		brandService.addBrand(brand);
		return new Srm().setResultCode("0").setResultMessage("添加品牌成功");
	}
	
	@RequestMapping("/update")
	public Srm updateBrand(Brand brand){
		brandService.updateBrand(brand);
		return new Srm().setResultCode("0").setResultMessage("修改品牌成功");
	}
	
	@RequestMapping("/delete")
	public Srm deleteBrand(Integer brandId){
		brandService.deleteBrand(brandId);
		return new Srm().setResultCode("0").setResultMessage("删除品牌成功");
	}
	
	@RequestMapping("/query")
	public Srm queryBrand(Brand brand, HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		Integer pageNum = params.getInt("page");
		Integer pageSize = params.getInt("limit");
		
		PageInfo<Brand> page = brandService.queryBrand(brand, pageNum, pageSize);
		return new Srm().setResultCode("0").setResultMessage("查询品牌成功").buildPageInfo(page);
	}
	
	@RequestMapping("/queryAll")
	public Srm queryBrandAll(Brand brand){
		List<Brand> list = brandService.queryBrandAll(brand);
		return new Srm().setResultCode("0").setResultMessage("查询所有品牌成功").addAll(list);
	}
	
}
