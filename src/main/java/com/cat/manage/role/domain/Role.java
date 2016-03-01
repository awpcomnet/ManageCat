package com.cat.manage.role.domain;

import java.io.Serializable;
import java.util.List;

import com.cat.manage.permission.domain.Permission;


/**
 * 
 * @Description: 角色领域模型
 * @author 王航
 * @date 2016年3月1日 上午10:52:53
 */
public class Role implements Serializable{
	private Integer id;//编号
	private String name;//角色名称
	private String code;//代码
	private String permissionIds;//权限编号
	private List<Permission> permissions;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", code=" + code
				+ ", permissionIds=" + permissionIds + "]";
	}

	private static final long serialVersionUID = 4608503214771783273L;

}
