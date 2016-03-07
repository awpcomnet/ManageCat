package com.cat.manage.order.serviceTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cat.manage.common.util.Md5Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class Password {
	
	@Test
	public void passwordtest(){
		LOG.info("输出的密码:123456  盐:8487");
		LOG.info("加密后:"+Md5Util.digest("123456" + "8487"));
		
	}
	
	
	private static final Logger LOG = LoggerFactory.getLogger(Password.class);
}
