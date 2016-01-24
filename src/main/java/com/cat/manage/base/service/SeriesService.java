package com.cat.manage.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.base.dao.SeriesDao;
import com.cat.manage.base.domain.Series;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 系列服务
 * @author wanghang
 *
 */
@Service
public class SeriesService {
	@Autowired
	private SeriesDao seriesDao;
	
	/**
	 * 添加系列信息
	 * @param series
	 */
	public void addSeries(Series series){
		seriesDao.addSeries(series);
	}
	
	/**
	 * 修改系列信息
	 * @param series
	 */
	public void updateSeries(Series series){
		seriesDao.updateSeries(series);
	}
	
	/**
	 * 删除系列信息
	 * @param seriesId
	 */
	public void deleteSeries(Integer seriesId){
		seriesDao.deleteSeries(seriesId);
	}
	
	/**
	 * 查询系列信息
	 * @param series
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Series> querySeries(Series series, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Series> list = seriesDao.querySeries(series);
		PageInfo<Series> page = new PageInfo<Series>(list);
		return page;
	}
}
