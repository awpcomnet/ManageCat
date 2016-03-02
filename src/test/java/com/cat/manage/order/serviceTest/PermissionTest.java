package com.cat.manage.order.serviceTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cat.manage.permission.domain.Permission;
import com.cat.manage.permission.service.PermissionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class PermissionTest {

	@Autowired
	private PermissionService permissionService;//.readPermissionTree();
	
	
	@Test
	public void testAddOrder(){
		
		LOG.info("测试添加订单开始");
		
		Permission permission = permissionService.readPermissionTree();
		LOG.info(permission.toString());
		LOG.info("测试添加订单结束");
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(PermissionTest.class);
}
