package com.cat.manage.base.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 单品领域模型
 * @author wanghang
 *
 */
public class Singleproduct implements Serializable{
	private Integer singleId;//单品ID
	private String singleName;//单品名称
	private String singleEname;//单品英文名称
	private String ofOrigin;//所属系列
	private String capacity;//规格容量
	private String unit;//单位
	private String isUse;//是否使用  0-否 1-是
	private Date createDate;//创建时间
	private String createBy;//创建人
	private Date updateDate;//更新时间
	private String updateBy;//更新人
	private String createDateFormat;//格式化后的创建时间
	private String updateDateFormat;//格式化后的更新时间
	
	public Integer getSingleId() {
		return singleId;
	}

	public void setSingleId(Integer singleId) {
		this.singleId = singleId;
	}

	public String getSingleName() {
		return singleName;
	}

	public void setSingleName(String singleName) {
		this.singleName = singleName;
	}

	public String getSingleEname() {
		return singleEname;
	}

	public void setSingleEname(String singleEname) {
		this.singleEname = singleEname;
	}

	public String getOfOrigin() {
		return ofOrigin;
	}

	public void setOfOrigin(String ofOrigin) {
		this.ofOrigin = ofOrigin;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getCreateDateFormat() {
		return createDateFormat;
	}

	public void setCreateDateFormat(String createDateFormat) {
		this.createDateFormat = createDateFormat;
	}

	public String getUpdateDateFormat() {
		return updateDateFormat;
	}

	public void setUpdateDateFormat(String updateDateFormat) {
		this.updateDateFormat = updateDateFormat;
	}

	@Override
	public String toString() {
		return "Singleproduct [singleId=" + singleId + ", singleName="
				+ singleName + ", singleEname=" + singleEname + ", ofOrigin="
				+ ofOrigin + ", capacity=" + capacity + ", unit=" + unit
				+ ", isUse=" + isUse + ", createDate=" + createDate
				+ ", createBy=" + createBy + ", updateDate=" + updateDate
				+ ", updateBy=" + updateBy + ", createDateFormat="
				+ createDateFormat + ", updateDateFormat=" + updateDateFormat
				+ "]";
	}

	private static final long serialVersionUID = -3618065874398768630L;
}
