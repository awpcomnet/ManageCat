package com.cat.manage.dict.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 字典类别
 * @author 
 * @date 2015年11月17日 上午9:46:16
 */
public class Dict implements Serializable {

	private int id;
	private String name;
	private String code;
	private String type;
	private String description;

	private List<DictItem> items;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<DictItem> getItems() {
		return items;
	}

	public void setItems(List<DictItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Dict [id=" + id + ", name=" + name + ", code=" + code
				+ ", type=" + type + ", description=" + description
				+ ", items=" + items + "]";
	}

	private static final long serialVersionUID = -6932011224031718164L;
}
