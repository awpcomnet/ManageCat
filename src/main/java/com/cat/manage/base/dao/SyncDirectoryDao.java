package com.cat.manage.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.base.domain.SyncDirectory;

/**
 * 单品/系列 同步目录 DAO
 * @author wanghang
 *
 */
@Repository
public interface SyncDirectoryDao {
	
	/**
	 * 添加同步目录
	 * @param syncDirectory
	 */
	public void addSyncDirectory(SyncDirectory syncDirectory);
	
	/**
	 * 根据同步目录唯一标识删除记录
	 * @param syncDirectoryId
	 */
	public void deleteSyncDirectoryById(Integer syncDirectoryId);
	
	/**
	 * 根据同步目录目录标记 删除记录
	 * @param syncDirectoryId
	 */
	public void deleteSyncDirectoryBySyncFlag(String syncFlag);
	
	/**
	 * 查询同步目录
	 * @param syncDirectory
	 * @return
	 */
	public List<SyncDirectory> querySyncDirectory(SyncDirectory syncDirectory);
	
	/**
	 * 查询目录中最新的一条记录, 两个参数有且仅有一个时，查询有效
	 * @param seriesId
	 * @param singleId
	 * @return
	 */
	public SyncDirectory querySyncDirectoryForNew(Integer seriesId, Integer singleId);
}
