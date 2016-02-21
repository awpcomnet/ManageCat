//package com.cat.manage.order.serviceTest;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.cat.manage.order.domain.Order;
//import com.cat.manage.order.service.OrderService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath*:applicationContext.xml")
//public class OrderTest {
//
//	@Autowired
//	private OrderService orderService;
//	
//	@Test
//	public void testAddOrder(){
//		LOG.info("测试添加订单开始");
//		Order order = new Order();
//		order.setForeignState("0");
//		order.setTransfer("0");
//		order.setAffirmState("0");
//		
//		orderService.addOrder(order);
//		LOG.info("测试添加订单结束");
//	}
//	
//	private static final Logger LOG = LoggerFactory.getLogger(OrderTest.class);
//}
