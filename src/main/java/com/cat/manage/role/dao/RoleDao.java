package com.cat.manage.role.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cat.manage.role.domain.Role;

/**
 * 
 * @Description: 角色Dao
 * @author 王航
 * @date 2016年3月1日 上午10:55:14
 */
@Repository
public interface RoleDao {
	/**
	 * 添加角色信息
	 * @param role
	 */
	public void addRole(Role role);
	
	/**
	 * 修改角色信息
	 * @param role
	 */
	public void updateRole(Role role);
	
	/**
	 * 根据角色唯一编号删除角色信息
	 * @param id
	 */
	public void deleteRole(Integer[] id);
	
	/**
	 * 查询所有角色信息
	 * @return
	 */
	public List<Role> queryAll();
	
	/**
	 * 删除角色权限信息
	 */
	void deleteRolePermission(Integer roleId);
	
	/**
	 * 增加角色权限信息
	 */
	void insertRolePermission(Map<String, Object> rolePermission);
	
	/**
	 * 查询某角色的所有权限信息
	 */
	List<Map<String, Object>> getRolePermission(Integer roleId);
	
	/**
	 * 通过角色id查询栏目资源的id
	 */
	List<Integer> getCatalogIdByRoleId(Integer roleId);
	
	/**
	 * 根据角色编号查询角色信息
	 * @param id
	 * @return
	 */
	public Role queryRoleById(Integer id);
}
