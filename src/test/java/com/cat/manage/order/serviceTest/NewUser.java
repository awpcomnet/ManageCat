package com.cat.manage.order.serviceTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cat.manage.common.util.Md5Util;
import com.cat.manage.user.dao.UserDao;
import com.cat.manage.user.domain.User;
import com.cat.manage.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class NewUser {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void testAddOrder(){
		LOG.info("添加用户开始");
		User user = new User();
		
		String salt = Md5Util.getRandomSalt();
		String password = "wang89hang"+salt;
		
		user.setUsername("admin");
		user.setRealname("admin");
		user.setPassword(Md5Util.digest(password));
		user.setSalt(salt);
		user.setEmail("");
		user.setState("0");
		
		userDao.addUser(user);
		LOG.info("添加用户结束");
	}
	
	
	public void testFindUser(){
		LOG.info("=====>查询用户");
		User user = userService.findUserByUsername("taozi");
		System.out.println(user);
		LOG.info("=====<查询用户");
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(NewUser.class);
}
