package com.cat.manage.base.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 国家领域模型
 * @author wanghang
 *
 */
public class Country implements Serializable{
	private Integer countryId;//国家ID
	private String countryName;//国家名称
	private String countryEname;//国家英文名称
	private Date createDate;//创建时间
	private String createBy;//创建人
	private Date updateDate;//更新时间
	private String updateBy;//更新人
	private String createDateFormat;//格式化后的创建时间
	private String updateDateFormat;//格式化后的更新时间
	
	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryEname() {
		return countryEname;
	}

	public void setCountryEname(String countryEname) {
		this.countryEname = countryEname;
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
		return "Country [countryId=" + countryId + ", countryName="
				+ countryName + ", countryEname=" + countryEname
				+ ", createDate=" + createDate + ", createBy=" + createBy
				+ ", updateDate=" + updateDate + ", updateBy=" + updateBy
				+ ", createDateFormat=" + createDateFormat
				+ ", updateDateFormat=" + updateDateFormat + "]";
	}

	private static final long serialVersionUID = 6645305372613449995L;
}
