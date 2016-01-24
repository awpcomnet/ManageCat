package com.cat.manage.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.base.domain.Series;
import com.cat.manage.base.service.SeriesService;
import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/Series")
public class SeriesController {
	@Autowired
	private SeriesService seriesService;
	
	@RequestMapping("/add")
	public Srm addSeries(Series series){
		seriesService.addSeries(series);
		return new Srm().setResultCode("0").setResultMessage("添加系列信息成功");
	}
	
	@RequestMapping("/update")
	public Srm updateSeries(Series series){
		seriesService.updateSeries(series);
		return new Srm().setResultCode("0").setResultMessage("修改系列信息成功");
	}
	
	@RequestMapping("/delete")
	public Srm deleteSeries(Integer seriesId){
		seriesService.deleteSeries(seriesId);
		return new Srm().setResultCode("0").setResultMessage("删除系列信息成功");
	}
	
	@RequestMapping("/query")
	public Srm querySeries(Series series, HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		Integer pageNum = params.getInt("page");
		Integer pageSize = params.getInt("limit");
		
		PageInfo<Series> page = seriesService.querySeries(series, pageNum, pageSize);
		return new Srm().setResultCode("0").setResultMessage("查询系列信息成功").buildPageInfo(page);
	}
}
