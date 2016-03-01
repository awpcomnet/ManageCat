package com.cat.manage.permission.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.permission.domain.Permission;

/**
 * 
 * @Description: 权限dao
 * @author 王航
 * @date 2016年3月1日 上午11:20:14
 */
@Repository
public interface PermissionDao {
	/**
	 * 添加权限
	 * @param permission
	 */
	public void addPermission(Permission permission);
	
	/**
	 * 修改权限
	 * @param permission
	 */
	public void updatePermission(Permission permission);
	
	/**
	 * 根据权限唯一编号删除信息
	 * @param id
	 */
	public void deletePermissionByIds(Integer[] id);
	
	/**
	 * 查询所有权限信息
	 * @return
	 */
	public List<Permission> queryPermissionAll();
	
	/**
     * 根据查询条件查询符合的记录
     */
    public List<Permission> queryPermissionByCondition(Permission permission);
    
    /**
     * 读取某个目录权限
     */
    public Permission getCatalogPermissionInfo(Integer id);
    
    /**
     * 读取栏目权限
     */
    public List<Permission> getCatalogPermission(Integer id);
    
    /**
     * 读取栏目下的功能权限
     */
    public List<Permission> getFunctionPermission(Integer id);
    
    /**
     * 根据角色ID在角色权限关系表中查询权限ID
     * @param roleId
     * @return
     */
    public List<Integer> getPermissionIdByRelationRoleId(Integer roleId);
    
    /**
     * 根据权限编号查询权限信息
     * @param id
     * @return
     */
    public Permission getPermissionById(Integer id);
    
}
