package com.cat.manage.batch.service;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.batch.dao.BatchDao;
import com.cat.manage.batch.domain.Batch;
import com.cat.manage.common.exception.BusinessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * 批次号服务类
 * @author wanghang
 *
 */
@Service
public class BatchService {
	
	private static final String FIRSTNUMBER = "01";
	
	@Autowired
	private BatchDao batchDao;
	
	/**
	 * 插入默认批次号
	 * 生成规则
	 * yyyyMMdd+2位序列
	 */
	public String addBatch(){
		String batchNo = "";
		
		DateTime now = new DateTime(new Date());
		String timeStr = now.toString("yyyyMMdd");
		
		List<Batch> list = Lists.newArrayList();
		Batch querybBatch = new Batch();
		querybBatch.setCreateDateFormat(timeStr);
		list = batchDao.queryBatch(querybBatch);
		
		if(list == null || list.size() <= 0){
			batchNo = timeStr + FIRSTNUMBER;
		} else {
			Batch existBatch = list.get(0);
			String existBatchNo = existBatch.getBatchNo();
			int existSerialNumber = Integer.parseInt(existBatchNo.substring(existBatchNo.length()-1, existBatchNo.length()));
			existSerialNumber++;
			batchNo = timeStr + String.format("%02d", existSerialNumber);
		}
		
		if(Strings.isNullOrEmpty(batchNo))
			throw new BusinessException("1", "批次号生成失败");
		
		Batch batch = new Batch();
		batch.setBatchNo(batchNo);
		batchDao.addBatch(batch);
		
		return batchNo;
	}
	
	/**
	 * 查询所有批次号
	 * @param batch
	 * @return
	 */
	public List<Batch> queryBatchForList(Batch batch){
		return batchDao.queryBatch(batch);
	}
	
	/**
	 * 分页查询批次号
	 * @param batch
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Batch> queryBatchForPage(Batch batch, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Batch> list = batchDao.queryBatch(batch);
		PageInfo<Batch> page = new PageInfo<Batch>(list);
		return page;
	}
	
	/**
	 * 检查 批次号 是否符合要求
	 * @param batchNo
	 * @return
	 */
	public boolean checkBatchNo(String batchNo){
		boolean isOk = true;
		
		if(Strings.isNullOrEmpty(batchNo)){
			isOk = false;
			LOG.info("批次号不能为空");
		}
		if(batchNo.length() != 10){
			isOk = false;
			LOG.info("批次号长度非法["+batchNo+"]");
		}
		
		Batch batch = new Batch();
		batch.setBatchNo(batchNo);
		List<Batch> list = batchDao.queryBatch(batch);
		if(list == null || list.size() <= 0){
			isOk = false;
			LOG.info("批次号不存在["+batchNo+"]");
		}
		
		return isOk;
	}
	
	private static final transient Logger LOG = LoggerFactory.getLogger(BatchService.class);
}
