package com.cat.manage.dict.domain;

import java.io.Serializable;

/**
 * @Description: 字典项
 * @author 
 * @date 2015年11月17日 上午9:46:36
 */
public class DictItem implements Serializable {

	private int id;
	private int typeId;
	private String name;
	private String value;
	private String code;
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "DictItem [id=" + id + ", typeId=" + typeId + ", name=" + name
				+ ", value=" + value + ", code=" + code + ", description="
				+ description + "]";
	}

	private static final long serialVersionUID = -6932011224031718164L;
}
