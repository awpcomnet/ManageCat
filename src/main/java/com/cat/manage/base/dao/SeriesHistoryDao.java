package com.cat.manage.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.base.domain.Series;
import com.cat.manage.base.domain.SeriesHistory;

/**
 * 系列历史Dao
 * @author wanghang
 *
 */
@Repository
public interface SeriesHistoryDao {
	/**
	 * 添加到系列历史表
	 * @param series
	 * @param synchronization
	 */
	public void addSeriesHistory(Series series, String synchronization);
	
	/**
	 * 更新系列历史表
	 * @param seriesHis
	 */
	public void updateSeriesHistory(SeriesHistory seriesHis);
	
	/**
	 * 删除系列历史表记录
	 * @param seriesHis
	 */
	public void deleteSeriesHistory(SeriesHistory seriesHis);
	
	/**
	 * 查询系列历史表记录
	 * @param seriesHis
	 * @return
	 */
	public SeriesHistory querySeriesHistoryBySeriesHisId(Integer seriesHisId);
	
	/**
	 * 根据系列唯一编号查询历史记录
	 * @param seriesId
	 * @return
	 */
	public SeriesHistory querySeriesHistoryBySeriesId(Integer seriesId);
}
