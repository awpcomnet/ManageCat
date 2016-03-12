package com.cat.manage.batch.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.batch.domain.Batch;

/**
 * 批次号DAO
 * @author wanghang
 *
 */
@Repository
public interface BatchDao {
	
	/**
	 * 插入批次号
	 * @param batch
	 */
	public void addBatch(Batch batch);
	
	/**
	 * 查询批次号
	 * @param batch
	 * @return
	 */
	public List<Batch> queryBatch(Batch batch);
}
