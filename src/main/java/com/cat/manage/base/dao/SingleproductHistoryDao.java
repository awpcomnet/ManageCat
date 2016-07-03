package com.cat.manage.base.dao;

import org.springframework.stereotype.Repository;

import com.cat.manage.base.domain.Singleproduct;
import com.cat.manage.base.domain.SingleproductHistory;

/**
 * 单品Dao
 * @author wanghang
 *
 */
@Repository
public interface SingleproductHistoryDao {
	/**
	 * 添加到单品历史表
	 * @param singleproduct
	 */
	public void addSingleproductHistory(Singleproduct singleproduct);
	
	/**
	 * 修改单品历史表
	 * @param singleproduct
	 */
	public void updateSingleproductHistory(SingleproductHistory singleproductHistory);
	
	/**
	 * 删除单品历史表记录
	 * @param singleproduct
	 */
	public void deleteSingleproductHistoryBySingleHisId(SingleproductHistory singleproductHistory);
	
	/**
	 * 删除单品历史记录 （系列唯一编号）
	 * @param seriesId
	 */
	public void deleteSingleproductHistoryBySeriesId(Integer seriesId);
	
	/**
	 * 根据单品历史表唯一编号查询
	 * @param singleHisId
	 * @return
	 */
	public SingleproductHistory querySingleproductBySingleHisId(Integer singleHisId);
	
	/**
	 * 根据单品唯一编号查询记录
	 * @param singleId
	 * @return
	 */
	public SingleproductHistory querySingleproductBySingleId(Integer singleId);
	
}
