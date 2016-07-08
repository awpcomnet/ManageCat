package com.cat.manage.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.base.domain.SyncDetail;

/**
 * 单品/系列 同步详情  DAO
 * @author wanghang
 *
 */
@Repository
public interface SyncDetailDao {
	
	/**
	 * 添加同步详情
	 * @param orderId
	 */
	public void addDetail(SyncDetail syncDetail);
	
	/**
	 * 查询同步详情
	 * @param syncDetail
	 * @return
	 */
	public List<SyncDetail> queryDetail(SyncDetail syncDetail);
	
	/**
	 * 根据同步详情表唯一编号删除同步记录
	 * @param syncId
	 */
	public void deleteDetailBySyncId(Integer syncId);
	
	/**
	 * 根据同步标记删除同步记录
	 * @param syncFlag
	 */
	public void deleteDetailBySyncFlag(String syncFlag);
	
	/**
	 * 根据订单唯一编号删除同步记录
	 * @param orderId
	 */
	public void deleteDetailByOrderId(Integer orderId);
}
