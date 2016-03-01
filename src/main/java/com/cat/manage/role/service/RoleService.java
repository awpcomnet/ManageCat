package com.cat.manage.role.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.role.dao.RoleDao;
import com.cat.manage.role.domain.Role;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 角色服务类
 * @author 王航
 * @date 2016年3月1日 上午11:14:19
 */
@Service
public class RoleService {
	@Autowired
	private RoleDao roleDao;
	
	/**
	 * 查询所有角色
	 * @return
	 */
	public List<Role> findAll(){
		return roleDao.queryAll();
	}
	
	/**
	 * 分页查询角色信息
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Role> getRolePage(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<Role> roles = roleDao.queryAll();
        PageInfo<Role> rolePage = new PageInfo<Role>(roles);
        
        return rolePage;
    }
	
	/**
	 * 添加角色信息
	 * @param role
	 */
	public void addRole(Role role) {
        roleDao.addRole(role);
    }
	
	/**
	 * 修改角色信息
	 * @param role
	 */
	public void updateRole(Role role) {
        roleDao.updateRole(role);
    }
	
	/**
	 * 删除角色信息
	 * @param ids
	 */
	public void deleteRole(Integer[] ids) {
        roleDao.deleteRole(ids);
    }
	
	/**
	 * 更新角色权限信息
	 * @param roleId
	 * @param rolePermission
	 */
	public void updateRolePermission(int roleId, List<Map<String, Integer>> rolePermission) {
        roleDao.deleteRolePermission(roleId);
        for (Iterator<Map<String, Integer>> it = rolePermission.iterator(); it.hasNext();) {
            Map<String, Integer> rolePer = it.next();
            Map<String, Object> rolePerDb = new HashMap<String, Object>();
            rolePerDb.put("roleId", rolePer.get("roleId"));
            rolePerDb.put("permissionId", rolePer.get("permissionId"));
            rolePerDb.put("permissionType", rolePer.get("permissionType").toString());
            
            roleDao.insertRolePermission(rolePerDb);
        }
    }
	
	/**
	 * 获取角色权限关系信息
	 * @param roleId
	 * @return
	 */
	public List<Map<String, Object>> getRolePermission(Integer roleId) {
        return roleDao.getRolePermission(roleId);
    }
	
	/**
	 * 根据角色ID获取栏目ID
	 * @param roleId
	 * @return
	 */
	public List<Integer> getCatalogIdByRoleId(Integer roleId) {
        return roleDao.getCatalogIdByRoleId(roleId);
    }
	
}
