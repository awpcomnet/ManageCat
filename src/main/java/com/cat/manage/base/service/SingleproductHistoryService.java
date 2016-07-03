package com.cat.manage.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.base.dao.SingleproductHistoryDao;
import com.cat.manage.base.domain.Singleproduct;
import com.cat.manage.base.domain.SingleproductHistory;

/**
 * 单品历史服务
 * @author wanghang
 *
 */
@Service
public class SingleproductHistoryService {
	@Autowired
	private SingleproductHistoryDao singleproductHisDao;
	
	/**
	 * 添加单品历史记录
	 * @param single
	 */
	public void addSingleproductHistory(Singleproduct single){
		singleproductHisDao.addSingleproductHistory(single);
	}
	
	/**
	 * 修改单品历史记录
	 * @param singleproductHistory
	 */
	public void updateSingleproductHistory(SingleproductHistory singleproductHistory){
		singleproductHisDao.updateSingleproductHistory(singleproductHistory);
	}
	
	/**
	 * 删除单品历史记录 (历史记录唯一编号)
	 * @param singleproductHistory
	 */
	public void deleteSingleproductHistory(SingleproductHistory singleproductHistory){
		singleproductHisDao.deleteSingleproductHistoryBySingleHisId(singleproductHistory);
	}
	
	/**
	 * 根据单品唯一编号删除历史记录
	 * @param singleId
	 */
	public void deleteSingleproductHistory(Integer singleId){
		SingleproductHistory singleHis = this.querySingleHistoryBySingleId(singleId);
		
		if(singleHis != null)
			this.deleteSingleproductHistory(singleHis);
	}
	
	/**
	 * 根据系列唯一编号删除 单品历史记录
	 * @param seriesId
	 */
	public void deleteSingleproductHistoryBySeriesId(Integer seriesId){
		singleproductHisDao.deleteSingleproductHistoryBySeriesId(seriesId);
	}
	
	/**
	 * 根据单品历史记录的唯一编号查询
	 * @param singleHisId
	 * @return
	 */
	public SingleproductHistory querySingleHistoryBySingleHisId(Integer singleHisId){
		return singleproductHisDao.querySingleproductBySingleHisId(singleHisId);
	}
	
	/**
	 * 根据单品的唯一记录查询历史记录
	 * @param singleId
	 * @return
	 */
	public SingleproductHistory querySingleHistoryBySingleId(Integer singleId){
		return singleproductHisDao.querySingleproductBySingleId(singleId);
	}
}
