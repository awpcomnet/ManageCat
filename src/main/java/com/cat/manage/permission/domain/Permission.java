package com.cat.manage.permission.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @Description: 权限领域模型
 * @author 王航
 * @date 2016年3月1日 上午11:17:08
 */
public class Permission implements Serializable{
	private Integer id;//编号
	private String name;//权限名称
	private String code;//代码
	private String url;//url
	private Integer catalogId;//栏目ID
	private String Type;//类型
	
	List<Permission> subPermissions;
	
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}

	public List<Permission> getSubPermissions() {
		return subPermissions;
	}

	public void setSubPermissions(List<Permission> subPermissions) {
		this.subPermissions = subPermissions;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	@Override
	public String toString() {
		return "Permission [id=" + id + ", name=" + name + ", code=" + code
				+ ", url=" + url + ", catalogId=" + catalogId + ", Type="
				+ Type + ", subPermissions=" + subPermissions + "]";
	}

	private static final long serialVersionUID = 6048676420072753858L;

}
