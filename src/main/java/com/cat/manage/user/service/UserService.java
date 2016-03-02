package com.cat.manage.user.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.common.util.Md5Util;
import com.cat.manage.permission.domain.Permission;
import com.cat.manage.permission.service.PermissionService;
import com.cat.manage.role.domain.Role;
import com.cat.manage.role.service.RoleService;
import com.cat.manage.user.dao.UserDao;
import com.cat.manage.user.domain.User;
import com.cat.manage.user.service.exception.LoginException;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @Description: 管理系统用户相关业务
 * @author 
 * @date 2015年10月20日 下午3:30:17
 */
@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;
	
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
		List<User> resultList = Lists.newArrayList();
		List<User> list = userDao.getAllUsers();
		for(int i=0, len=list.size(); i<len; i++){
			User user = list.get(i);
			String roles = "";
			List<Integer> rolesList = userDao.getRoleidByUserid(user.getUserId());
			if(rolesList != null){
				for(int j=0; j<rolesList.size(); j++){
					roles += rolesList.get(j);
					if(j != rolesList.size() - 1)
						roles += ",";
				}
			}
			user.setRoles(roles);
			resultList.add(user);
		}
		
		return resultList;
	}
	
	/**
	 * 新增加用户
	 */
	public void addUser(User user, String[] roles) {
		// 0, 检查用户是否已存在
		User queryUser = userDao.getUserByUsername(user.getUsername());
		if (queryUser != null)
			throw new BusinessException("2", "用户名为[" + user.getUsername()
					+ "]的用户已经存在!");

		// 1, 增加用户基本信息
		userDao.addUser(user);
		
		//查询用户信息
		user = userDao.queryByUserName(user.getUsername());

		// 2, 获得用户id
		int id = user.getUserId();
		for (int i = 0, len = roles.length; i < len; i++) {
			if(Strings.isNullOrEmpty(roles[i])){
				continue;
			}
			int roleId = Integer.valueOf(roles[i]);
			Map<String, Integer> relation = new HashMap<String, Integer>();
			relation.put("userId", id);
			relation.put("roleId", roleId);
			userDao.addUserRoleRelation(relation);
		}

	}
	
	/**
	 * 更新用户数据
	 */
	public void updateUser(User user, String[] roles) {
	    // 1, 更新用户数据
	    userDao.updateUser(user);
	    
	    // 2，删除用户所有角色
	    userDao.deleteUserRoleRelationByUserId(user.getUserId());
	    
	    //3.添加角色
	    for(String r : roles){
	    	if(Strings.isNullOrEmpty(r))
	    		continue;
	    	
	    	Map<String, Integer> map = Maps.newHashMap();
	    	map.put("userId", user.getUserId());
	    	map.put("roleId", Integer.valueOf(r));
	    	userDao.addUserRoleRelation(map);
	    }
	    
	}
	
	/**
	 * 通过用户名查找用户所拥有的角色名
	 */
	public Set<String> findRolesByUsername(String username) {
		Set<String> set = Sets.newHashSet();
		
		User user = userDao.getUserByUsername(username);
		if(user == null)
			throw new BusinessException("1", "["+username+"]用户不存在");
		
		List<Integer> roleIds = userDao.getRoleidByUserid(user.getUserId());
		if(roleIds == null)
			return null;
		
		for(int i=0,len=roleIds.size(); i<len; i++){
			Integer roleId = roleIds.get(i);
			Role role = roleService.queryRoleById(roleId);
			if(role == null)
				continue;
			set.add(role.getCode());
		}
	    return set;
	}
	
	/**
	 * 通过用户名查找用户所拥有的权限名
	 */
	public Set<String> findPermissionByUsername(String username) {
		Set<String> set = Sets.newHashSet();
		
		User user = userDao.getUserByUsername(username);
		if(user == null)
			throw new BusinessException("1", "["+username+"]用户不存在");
		
		List<Integer> roleIds = userDao.getRoleidByUserid(user.getUserId());
		if(roleIds == null)
			return null;
		
		for(int i=0,len=roleIds.size(); i<len; i++){
			Integer roleId = roleIds.get(i);
			Role role = roleService.queryRoleById(roleId);
			if(role == null)
				continue;
			//查询关联的权限id
			List<Integer> permissionIds = permissionService.getPermissionIdByRelationRoleId(roleId);
			if(permissionIds == null)
				continue;
			
			for(int j=0, plen=permissionIds.size(); j<plen; j++){
				Permission permission = permissionService.getPermissionById(permissionIds.get(j));
				if(permission == null)
					continue;
				set.add(permission.getCode());
			}
			
		}
	    return  set;
	}
	
	/**
	 * 获得用户可访问栏目
	 */
	public Set<Integer> getUserCatalog(Integer userid) {
	    Set<Integer> catalogSet = new HashSet<Integer>();
	    if (userid == null) {
	        return catalogSet;
	    }
	    
	    Set<Integer> roleIds = findRoleidByUserid(userid);
	    if (roleIds == null) {
	        return catalogSet;
	    }
	    
	    for (Iterator<Integer> it = roleIds.iterator(); it.hasNext();) {
	        Integer roleId = it.next();
	        List<Integer> perIds = roleService.getCatalogIdByRoleId(roleId);
	        if (perIds != null) {
	            for (int i = 0, len = perIds.size(); i < len; i++) {
	                catalogSet.add(perIds.get(i));
	            }
	        }
	    }
	    
	    return catalogSet;
	}
	
	/**
	 * 通过用户id查询该用户所拥有角色的id
	 */
	public Set<Integer> findRoleidByUserid(Integer userId) {
	    Set<Integer> roleId = new HashSet<Integer>();
	    if (userId == null) {
	        return roleId;
	    }
	    
	    List<Integer> roleIds = userDao.getRoleidByUserid(userId);
	    if (roleIds == null) {
	        return roleId;
	    }
	    
	    for (int i = 0, len = roleIds.size(); i < len; i++) {
	        roleId.add(roleIds.get(i));
	    }
	    
	    return roleId;
	}
}
