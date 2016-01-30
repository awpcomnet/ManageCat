package com.cat.manage.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.base.domain.Series;

/**
 * 系列Dao
 * @author wanghang
 *
 */
@Repository
public interface SeriesDao {
	
	/**
	 * 添加系列
	 * @param series
	 */
	public void addSeries(Series series);
	
	/**
	 * 修改系列
	 * @param series
	 */
	public void updateSeries(Series series);
	
	/**
	 * 删除系列
	 * @param seriesId
	 */
	public void deleteSeries(Integer seriesId);
	
	/**
	 * 查询系列
	 * @param series
	 * @return
	 */
	public List<Series> querySeries(Series series);
	
	/**
	 * 根据系列编号查询系列信息
	 * @param seriesId
	 * @return
	 */
	public Series querySeriesById(Integer seriesId);
}
