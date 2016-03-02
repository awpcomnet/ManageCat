package com.cat.manage.permission.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.permission.dao.PermissionDao;
import com.cat.manage.permission.domain.Permission;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 权限服务类
 * @author 王航
 * @date 2016年3月1日 上午11:31:33
 */
@Service
public class PermissionService {
	@Autowired
	private PermissionDao permissionDao;
	
	/**
     * 获得权限的
     */
    public PageInfo<Permission> getPermissionPageWithCondition(int pageNumber, int pageSize, Permission permission) {
        PageHelper.startPage(pageNumber, pageSize);
        List<Permission> permissions = permissionDao.queryPermissionByCondition(permission);
        PageInfo<Permission> page = new PageInfo<Permission>(permissions);
        
        return page;
    }
    
    /** 
     * 增加权限
     */
    public void addPermission(Permission permission) {
        permissionDao.addPermission(permission);
    }
    
    /**
     * 更新权限
     */
    public void updatePermission(Permission permission) {
        permissionDao.updatePermission(permission);
    }
    
    /**
     * 删除权限
     */
    public void deletePermission(Integer[] ids) {
        permissionDao.deletePermissionByIds(ids);
    }
    
    /**
     * 构建权限树
     */
    public Permission readPermissionTree() {
        Permission root =  permissionDao.getCatalogPermissionInfo(0);//根目录
        buildPermissionTree(root);
        
        return root;
    }
    
    private void buildPermissionTree(Permission permission) {
        if (permission == null)
            return;
        
        List<Permission> catalogs = permissionDao.getCatalogPermission(permission.getId());
        if (catalogs != null && catalogs.size() > 0) {
            permission.setSubPermissions(catalogs);
            //permission.setType("0");//非末节点
            
            for (Permission ca : catalogs) {
            	//ca.setType("0");
                buildPermissionTree(ca);
            }
        } else {
            List<Permission> permissions = permissionDao.getFunctionPermission(permission.getId());
            permission.setSubPermissions(permissions);
            //permission.setType("1");//末节点
        }
    }
    
    /**
     * 根据角色ID在角色权限关系表中查询权限ID
     * @param roleId
     * @return
     */
    public List<Integer> getPermissionIdByRelationRoleId(Integer roleId){
    	return permissionDao.getPermissionIdByRelationRoleId(roleId);
    }
    
    /**
     * 根据权限编号查询权限信息
     * @param id
     * @return
     */
    public Permission getPermissionById(Integer id){
    	return permissionDao.getPermissionById(id);
    }
}
