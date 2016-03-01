package com.cat.manage.user.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cat.manage.user.domain.User;

@Repository
public interface UserDao {
	
	/**
	 * 通过用户名获得用户信息
	 */
	User getUserByUsername(String username);
	
	/**
	 * 查询获得所有用户
	 */
	List<User> getAllUsers();
	
	/**
	 * 新增一个用户
	 */
	void addUser(User user);
	
	/**
	 * 更新用户相关数据
	 */
	void updateUser(User user);
	
	/**
	 * 根据用户名查询用户信息
	 * @param userName
	 * @return
	 */
	User queryByUserName(String userName);
	
	/**
	 * 根据用户id的数组删除用户数组
	 */
	//void deleteByIds(int[] ids);
	
	/**
	 * 增加用户角色关系
	 */
	void addUserRoleRelation(Map<String, Integer> relation);
	
	/**
	 * 通过用户Id查询用户所拥有角色Id
	 */
	List<Integer> getRoleidByUserid(Integer id);
	
	/**
	 * 根据用户编号删除 用户角色关系信息
	 * @param userId
	 */
	public void deleteUserRoleRelationByUserId(Integer userId);
}
