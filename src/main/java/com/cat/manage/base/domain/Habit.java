package com.cat.manage.base.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 偏好领域模型
 * @author wanghang
 *
 */
public class Habit implements Serializable{
	private Integer id;//习惯记录唯一编号
	private String userId;//使用者唯一编号
	private String type;//类型
	private String habitKey;//键
	private String habitValue;//值
	private Date createDate;//创建时间
	private Date updateDate;//更新时间
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHabitKey() {
		return habitKey;
	}

	public void setHabitKey(String habitKey) {
		this.habitKey = habitKey;
	}

	public String getHabitValue() {
		return habitValue;
	}

	public void setHabitValue(String habitValue) {
		this.habitValue = habitValue;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "Habit [id=" + id + ", userId=" + userId + ", type=" + type
				+ ", habitKey=" + habitKey + ", habitValue=" + habitValue
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ "]";
	}

	private static final long serialVersionUID = -5168265479733066741L;

}
