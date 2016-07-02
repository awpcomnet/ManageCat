package com.cat.manage.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.base.dao.SeriesDao;
import com.cat.manage.base.domain.Brand;
import com.cat.manage.base.domain.Series;
import com.cat.manage.common.exception.BusinessException;
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
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private SingleproductService singleService;
	
	/**
	 * 添加系列信息
	 * @param series
	 */
	public boolean addSeries(Series series){
		Brand brand = brandService.queryBrandById(Integer.valueOf(series.getOfOrigin()));
		if(brand == null)
			throw new BusinessException("1", "品牌信息不存在");
		if(!"1".equals(brand.getIsUse()))
			throw new BusinessException("1", "品牌已失效");
		
		//检测品牌是否存在(中文名|英文名 任意一个重复则已存在)
		List<Series> list = seriesDao.querySeriesAccurateForName(series);
		if(list != null && list.size() >= 1)
			return false;
		
		seriesDao.addSeries(series);
		return true;
	}
	
	/**
	 * 修改系列信息
	 * @param series
	 */
	public boolean updateSeries(Series series){
		Brand brand = brandService.queryBrandById(Integer.valueOf(series.getOfOrigin()));
		if(brand == null)
			throw new BusinessException("1", "品牌信息不存在");
		if(!"1".equals(brand.getIsUse()))
			throw new BusinessException("1", "品牌已失效");
		
		//检测品牌是否存在(中文名|英文名 任意一个重复则已存在)
		List<Series> list = seriesDao.querySeriesAccurateForName(series);
		if(list != null && list.size() >= 1)
			return false;
		
		seriesDao.updateSeries(series);
		return true;
	}
	
	/**
	 * 根据唯一编号删除系列信息
	 * @param seriesId
	 */
	public void deleteSeries(Integer seriesId){
		//删除系列信息
		seriesDao.deleteSeries(seriesId);
		
		//删除单品信息
		singleService.deleteSingleproductBySeriesId(new Integer[]{seriesId});
	}
	
	/**
	 * 根据品牌编号删除系列
	 * @param brandId
	 */
	public void deleteSeriesByBrandId(Integer brandId){
		seriesDao.deleteSeriesByBrandId(brandId);
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
	
	/**
	 * 查询所有系列信息
	 * @param series
	 * @return
	 */
	public List<Series> querySeriesAll(Series series){
		return seriesDao.querySeries(series);
	}
	
	/**
	 * 根据系列编号查询系列信息
	 * @param seriesId
	 * @return
	 */
	public Series querySeriesById(Integer seriesId){
		return seriesDao.querySeriesById(seriesId);
	}
	
	/**
	 * 根据品牌查询系列信息
	 * @param brandId
	 * @return
	 */
	public List<Series> querySeriesByBrandId(Integer brandId){
		Series series = new Series();
		series.setOfOrigin(brandId+"");
		
		return seriesDao.querySeries(series);
	}
}
