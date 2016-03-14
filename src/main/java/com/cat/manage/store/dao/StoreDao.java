package com.cat.manage.store.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.shipped.domain.Shipped;
import com.cat.manage.store.domain.Store;

/**
 * 仓库DAO
 * @author wanghang
 *
 */
@Repository
public interface StoreDao {
	/**
	 * 添加仓库信息
	 * @param store
	 */
	public void addStore(Shipped shipped, Store store);
	
	/**
	 * 修改仓库信息
	 * @param store
	 */
	public void updateStore(Store store);
	
	/**
	 * 根据仓库唯一编号删除仓库信息
	 * @param id
	 */
	public void deleteStoreById(Integer id);
	
	/**
	 * 根据下单清单唯一编号删除仓库信息
	 * @param checkId
	 */
	public void deleteStoreByCheckId(Integer checkId);
	
	/**
	 * 查询仓库信息
	 * @param store
	 * @return
	 */
	public List<Store> queryStore(Store store, String[] includeStatus);
	
	/**
	 * 根据仓库唯一编号查询仓库信息
	 * @param id
	 * @return
	 */
	public Store queryStoreById(Integer id);
	
	/**
	 * 根据下单清单唯一编号查询仓库信息
	 * @param checkId
	 * @return
	 */
	public Store queryStoreByCheckId(Integer checkId);
	
	/**
	 * 根据邮寄清单唯一编号查询入库清单信息
	 * @param shippedId
	 * @return
	 */
	public Store queryStoreByShippedId(Integer shippedId);
	
	/**
	 * 根据入库清单唯一编号查询记录（多个）
	 * @param ids
	 * @return
	 */
	public List<Store> queryStoreByIds(Integer[] ids);
	
	/**
	 * 根据下单清单唯一编号（多个）查询入库信息
	 * @param checkIds
	 * @return
	 */
	public List<Store> queryStoreByCheckIds(Integer[] checkIds);
	
	/**
	 * 查询入库信息时间段内信息
	 * @param store
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Store> queryStoreForTimeQuantum(Store store, String startTime, String endTime);
	
	/**
	 * 查询仍有库存的所有记录
	 * @return
	 */
	public List<Store> queryStoreForValidity();
}
