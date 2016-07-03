package com.cat.manage.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.base.dao.SeriesHistoryDao;
import com.cat.manage.base.domain.Series;
import com.cat.manage.base.domain.SeriesHistory;

/**
 * 系列历史服务
 * @author wanghang
 *
 */
@Service
public class SeriesHisService {
	@Autowired
	private SeriesHistoryDao seriesHisDao;
	
	/**
	 * 添加系列历史表
	 * @param series
	 * @param Synchronization 是否需要同步 0-不需要 1-需要
	 */
	public void addSeriesHis(Series series, String synchronization){
		seriesHisDao.addSeriesHistory(series, synchronization);
	}
	
	/**
	 * 添加列历史表
	 * 需要同步
	 * @param series
	 */
	public void addSeriesHis(Series series){
		this.addSeriesHis(series, "1");
	}
	
	/**
	 * 修改系列历史表
	 * @param seriesHistory
	 */
	public void updateSeriesHis(SeriesHistory seriesHistory){
		seriesHisDao.updateSeriesHistory(seriesHistory);
	}
	
	/**
	 * 查询系列历史表
	 * @param seriesHistory
	 * @return
	 */
	public SeriesHistory querySeriesHisBySeriesHisId(Integer seriesHisId){
		return seriesHisDao.querySeriesHistoryBySeriesHisId(seriesHisId);
	}
	
	/**
	 * 查询系列历史信息
	 * @param seriesId
	 * @return
	 */
	public SeriesHistory querySeriesHisBySeriesId(Integer seriesId){
		return seriesHisDao.querySeriesHistoryBySeriesId(seriesId);
	}
	
	/**
	 * 删除系列历史表信息
	 * @param seriesHistory
	 */
	public void deleteSeriesHis(SeriesHistory seriesHistory){
		seriesHisDao.deleteSeriesHistory(seriesHistory);
	}
	
	/**
	 * 删除历史信息
	 * @param seriesId
	 */
	public void deleteSeriesHistoryInfo(Integer seriesId){
		//查询历史信息
		SeriesHistory seriesHistory = seriesHisDao.querySeriesHistoryBySeriesId(seriesId);
		
		if(seriesHistory.getSeriesHisId() != null)
			this.deleteSeriesHis(seriesHistory);
	}
}
