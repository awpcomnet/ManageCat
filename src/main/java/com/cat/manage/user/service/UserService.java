package com.cat.manage.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.common.util.Md5Util;
import com.cat.manage.user.dao.UserDao;
import com.cat.manage.user.domain.User;
import com.cat.manage.user.service.exception.LoginException;
import com.google.common.base.Strings;

/**
 * @Description: 管理系统用户相关业务
 * @author 
 * @date 2015年10月20日 下午3:30:17
 */
@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 用户登录方法
	 */
	public User login(String username, String password) throws LoginException {
		User user = userDao.getUserByUsername(username);
		
		if (user == null)
			throw new LoginException("用户不存在!");
		
		String userSavePassword = user.getPassword();
		String salt = user.getSalt();
		
		password += salt;
		String calcPassword = Md5Util.digest(password);
		
		if (Strings.isNullOrEmpty(calcPassword) || !calcPassword.equals(userSavePassword))
			throw new LoginException("密码错误!");
		
		return user;
	}
	
	
	/**
	 * 通过用户名查找用户
	 */
	public User findUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}
	
	/**
	 * 查找所有用户
	 */
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
	
	/**
	 * 新增加用户
	 */
	public void addUser(User user) {
	    // 0, 检查用户是否已存在
	    User queryUser = userDao.getUserByUsername(user.getUsername());
	    if (queryUser != null)
	        throw new BusinessException("2", "用户名为["+ user.getUsername() +"]的用户已经存在!");
	    
	    // 1, 增加用户基本信息
	    userDao.addUser(user);
	    
	}
	
	/**
	 * 更新用户数据
	 */
	public void updateUser(User user, String departments, String roles) {
	    // 1, 更新用户数据
	    userDao.updateUser(user);
	}
	
}
