package com.cat.manage.order.serviceTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cat.manage.base.service.SyncDetailService;
import com.cat.manage.check.domain.Check;
import com.cat.manage.check.service.CheckService;
import com.cat.manage.selled.service.SelledService;
import com.cat.manage.shipped.dao.ShippedDao;
import com.cat.manage.shipped.domain.Shipped;
import com.cat.manage.store.service.StoreService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class SyncTest {
	@Autowired
	private SyncDetailService syncDetailService;
	
	@Autowired
	private CheckService checkService;
	
	@Autowired
	private ShippedDao shippedDao;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private SelledService selledService;
	
	@Test
	public void SyncDetailTest(){
		LOG.info("同步详情测试开始");
		Check check = new Check();
		check.setSeriesId(1);
		
		List<Check> checkList = checkService.queryCheckForList(check, "", "");
		System.out.println(checkList);
		
		syncDetailService.addSyncDetailList(checkList, "ceshi");
		
		Shipped shipped = new Shipped();
		shipped.setSeriesId(6);
		
		List<Shipped> shippedList = shippedDao.queryShippedForSync(shipped);
		System.out.println(shippedList);
		
		syncDetailService.addSyncDetailList(shippedList, "zaiceshi");
		
		
		LOG.info("同步详情测试结束");
	}
	
	private static final transient Logger LOG = LoggerFactory.getLogger(SyncTest.class);
}
