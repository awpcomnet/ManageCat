package com.cat.manage.base.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 品牌领域模型
 * @author wanghang
 *
 */
public class Brand implements Serializable{
	private Integer brandId;//品牌ID
	private String brandName;//品牌名称
	private String brandEname;//品牌英文名称
	private String ofOrigin;//所属国家
	private String isUse;//是否使用  0-否 1-是
	private Date createDate;//创建时间 
	private String createBy;//创建人
	private Date updateDate;//更新时间
	private String updateBy;//更新人
	private String createDateFormat;//格式化后的创建时间
	private String updateDateFormat;//格式化后的更新时间
	
	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandEname() {
		return brandEname;
	}

	public void setBrandEname(String brandEname) {
		this.brandEname = brandEname;
	}

	public String getOfOrigin() {
		return ofOrigin;
	}

	public void setOfOrigin(String ofOrigin) {
		this.ofOrigin = ofOrigin;
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
		return "Brand [brandId=" + brandId + ", brandName=" + brandName
				+ ", brandEname=" + brandEname + ", ofOrigin=" + ofOrigin
				+ ", isUse=" + isUse + ", createDate=" + createDate
				+ ", createBy=" + createBy + ", updateDate=" + updateDate
				+ ", updateBy=" + updateBy + ", createDateFormat="
				+ createDateFormat + ", updateDateFormat=" + updateDateFormat
				+ "]";
	}

	private static final long serialVersionUID = -8360185937850155428L;
}
