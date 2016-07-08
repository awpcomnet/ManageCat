package com.cat.manage.base.controller;

import java.util.List;
import java.util.Map;

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
		//参数处理(去除空格)
		series.setSeriesEname(series.getSeriesEname().trim());
		series.setSeriesName(series.getSeriesName().trim());
		
		boolean isAdd = seriesService.addSeries(series);
		if(isAdd)
			return new Srm().setResultCode("0").setResultMessage("添加系列信息成功");
		else 
			return new Srm().setResultCode("1").setResultMessage("该系列已经存在，校验中文名["+series.getSeriesName()+"]和英文名["+series.getSeriesEname()+"]");
	}
	
	@RequestMapping("/update")
	public Srm updateSeries(Series series){
		//参数处理
		series.setSeriesEname(series.getSeriesEname().trim());
		series.setSeriesName(series.getSeriesName().trim());
		
		boolean isModify = seriesService.updateSeries(series);
		if(isModify)
			return new Srm().setResultCode("0").setResultMessage("修改系列信息成功");
		else
			return new Srm().setResultCode("1").setResultMessage("该系列已经存在，校验中文名["+series.getSeriesName()+"]和英文名["+series.getSeriesEname()+"]");
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
	
	@RequestMapping("/queryAll")
	public Srm querySeriesAll(Series series){
		List<Series> list = seriesService.querySeriesAll(series);
		return new Srm().setResultCode("0").setResultMessage("查询所有系列信息成功").addAll(list);
	}
	
	@RequestMapping("/recover")
	public Srm recoverSeries(Series series){
		seriesService.recoverSeries(series);
		return new Srm().setResultCode("0").setResultMessage("恢复系列历史信息成功");
	}
	
	@RequestMapping("/sync")
	public Srm syncSeries(Series series){
		Map<String, Integer> map = seriesService.syncSeries(series);
		String tip = "同步系列完成,本次同步影响[下单清单 "+map.get("check")+" 条]，[邮寄清单 "+map.get("shipped")+"  条]，"
				+ "[入库清单 "+map.get("store")+"  条]，[售出清单 "+map.get("selled")+"  条]";
		return new Srm().setResultCode("0").setResultMessage(tip);
	}
	
	@RequestMapping("/recoverSync")
	public Srm recoverSyncSeries(Series series){
		Map<String, Integer> map = seriesService.recoverSyncSeries(series);
		String tip = "恢复系列完成,本次恢复影响[下单清单 "+map.get("check")+" 条]，[邮寄清单 "+map.get("shipped")+"  条]，"
				+ "[入库清单 "+map.get("store")+"  条]，[售出清单 "+map.get("selled")+"  条]";
		return new Srm().setResultCode("0").setResultMessage(tip);
	}
}
