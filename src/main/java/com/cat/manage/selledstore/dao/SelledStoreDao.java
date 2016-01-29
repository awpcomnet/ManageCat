package com.cat.manage.selledstore.dao;

import org.springframework.stereotype.Repository;

import com.cat.manage.order.domain.SubOrder;

/**
 * 售出仓库DAO
 * @author wanghang
 *
 */
@Repository
public interface SelledStoreDao {
	
	/**
	 * 添加售出记录
	 */
	public void addSelledStore(SubOrder subOrder);
	
	/**
	 * 修改售出记录
	 * @param subOrder
	 */
	public void updateSelledStore(SubOrder subOrder);
	
	/**
	 * 删除售出记录
	 */
	public void deleteSelledStore(Integer selledStoreId);
}
