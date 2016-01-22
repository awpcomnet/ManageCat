package com.cat.manage.order.serviceTest;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cat.manage.order.dao.SubOrderDao;
import com.cat.manage.order.domain.SubOrder;
import com.cat.manage.order.service.SubOrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class SubOrderTest {

	@Autowired
	private SubOrderService subOrderService;
	
	@Autowired
	private SubOrderDao subOrderDao;
	
	@Test
	public void testAddOrder(){
		
		LOG.info("测试添加订单开始");
		SubOrder subOrder = new SubOrder();
		subOrder.setSuperOrderId(2);
		subOrder.setBrandId(1);
		subOrder.setSeriesId(1);
		subOrder.setSingleId(1);
		subOrder.setNum(10);
		subOrder.setOrderPrice(150.00);
		subOrder.setTransferPrice(10.00);
		subOrder.setCostPrice(160.00);
		subOrder.setSellingPrice(210.00);
		subOrder.setCurState("0");
		subOrderDao.addSubOrder(subOrder);
		LOG.info("测试添加订单结束");
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(SubOrderTest.class);
}
