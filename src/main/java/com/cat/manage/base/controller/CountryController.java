package com.cat.manage.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.base.domain.Country;
import com.cat.manage.base.service.CountryService;
import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/country")
public class CountryController {
	@Autowired
	private CountryService countryService;
	
	@RequestMapping("/add")
	public Srm addCountry(Country country){
		countryService.addCountry(country);
		return new Srm().setResultCode("0").setResultMessage("添加国家信息成功");
	}
	
	@RequestMapping("/update")
	public Srm updateCountry(Country country){
		countryService.updateCountry(country);
		return new Srm().setResultCode("0").setResultMessage("修改国家信息成功");
	}
	
	@RequestMapping("/delete")
	public Srm deleteCountry(Integer countryId){
		countryService.deleteCountry(countryId);
		return new Srm().setResultCode("0").setResultMessage("删除国家信息成功");
	}
	
	@RequestMapping("/query")
	public Srm queryCountry(Country country, HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		Integer pageNum = params.getInt("page");
		Integer pageSize = params.getInt("limit");
		
		PageInfo<Country> page = countryService.queryCountry(country, pageNum, pageSize);
		return new Srm().setResultCode("0").setResultMessage("查询国家信息成功").buildPageInfo(page);
	}
}
