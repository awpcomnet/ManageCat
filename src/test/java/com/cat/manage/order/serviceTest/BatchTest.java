package com.cat.manage.order.serviceTest;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cat.manage.batch.dao.BatchDao;
import com.cat.manage.batch.domain.Batch;
import com.cat.manage.batch.service.BatchService;
import com.google.common.collect.Lists;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class BatchTest {

	@Autowired
	private BatchDao batchDao;
	
	@Autowired
	private BatchService batchService;
	
	public void testBatchDao() throws FileNotFoundException{
		
		LOG.info("测试批次号开始");
		Batch batch = new Batch();
		batch.setId(null);
		batch.setBatchNo("");
		batch.setCreateDateFormat("20160311");
		
		List<Batch> batchList = Lists.newArrayList();
		batchList = batchDao.queryBatch(batch);
		for(Batch b: batchList){
			LOG.info("批次号信息:"+b.toString());
		}
		
		LOG.info("测试批次号结束");
	}
	
	@Test
	public void testBatchService(){
		LOG.info("测试批次号生成服务开始");
		batchService.addBatch();
		
		LOG.info("测试批次号生成服务结束");
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(BatchTest.class);
}
