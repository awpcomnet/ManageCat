package com.cat.manage.catalog.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 描述: 栏目的领域模型 
 * 作者: 
 */
public class Catalog implements Serializable {

	private int id;
	private int parentId;
	private String abbr;
	private String name;
	private String url;
	private String urlType;
	private String description;
	private int order;
	private String state;
	private String icon;
	private boolean leaf;
	private List<Catalog> subCatalogs;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<Catalog> getSubCatalogs() {
		return subCatalogs;
	}

	public void setSubCatalogs(List<Catalog> subCatalogs) {
		this.subCatalogs = subCatalogs;
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	@Override
	public String toString() {
		return "Catalog [id=" + id + ", parentId=" + parentId + ", abbr="
				+ abbr + ", name=" + name + ", url=" + url + ", urlType="
				+ urlType + ", description=" + description + ", order=" + order
				+ ", state=" + state + ", icon=" + icon + ", leaf=" + leaf
				+ ", subCatalogs=" + subCatalogs + "]";
	}

	private static final long serialVersionUID = 4202080082543585129L;
}
