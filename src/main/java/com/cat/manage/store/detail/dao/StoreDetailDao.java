package com.cat.manage.store.detail.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.store.detail.domain.StoreDetail;
import com.cat.manage.store.domain.Store;

/**
 * 仓库详情dao
 * @author wanghang
 *
 */
@Repository
public interface StoreDetailDao {
	
	/**
	 * 查询所有库存
	 * 1.只查询仍有库存的记录
	 * @return
	 */
	public List<Store> queryStoreAll(Store store);
	
	/**
	 * 查询所有库存详情
	 * 1.只查询仍有库存的记录
	 * @param store
	 * @return
	 */
	public List<StoreDetail> queryStoreByPayBy(Store store);
}
