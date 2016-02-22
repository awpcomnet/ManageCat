package com.cat.manage.order.serviceTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cat.manage.shipped.domain.Shipped;
import com.cat.manage.shipped.service.ShippedService;
import com.cat.manage.store.dao.StoreDao;
import com.cat.manage.store.domain.Store;
import com.cat.manage.store.service.StoreService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class StoreTest {

	@Autowired
	private StoreService storeService;
	
	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private ShippedService shippedService;
	
	
	@Test
	public void testAddOrder(){
		
		LOG.info("测试入库开始");
		Shipped shipped = shippedService.queryShippedById(1);
		Store store = new Store();
		store.setStoreStatus("2");
		store.setUnitPostage("11");
		store.setUnitPrice("230");
		store.setUnitCost("500");
		
		storeDao.addStore(shipped, store);
		LOG.info("测试入库结束");
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(StoreTest.class);
}
