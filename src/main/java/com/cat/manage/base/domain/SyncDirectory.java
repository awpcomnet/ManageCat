package com.cat.manage.base.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 单品/系列 同步订单目录 领域模型
 * @author wanghang
 *
 */
public class SyncDirectory implements Serializable{
	private Integer syncDirectoryId ;//同步表ID
	private String syncFlag ;//目录表标记
	private Integer brandId ;//品牌ID
	private Integer seriesId ;//系列ID 
	private Integer singleId ;//单品ID
	private String isUsable ;//是否可用  0-否 1-是 (暂时不适用这个字段)
	private Date createDate ;//创建时间
	private String createBy ;//创建人
	
	public Integer getSyncDirectoryId() {
		return syncDirectoryId;
	}

	public void setSyncDirectoryId(Integer syncDirectoryId) {
		this.syncDirectoryId = syncDirectoryId;
	}

	public String getSyncFlag() {
		return syncFlag;
	}

	public void setSyncFlag(String syncFlag) {
		this.syncFlag = syncFlag;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public Integer getSingleId() {
		return singleId;
	}

	public void setSingleId(Integer singleId) {
		this.singleId = singleId;
	}

	public String getIsUsable() {
		return isUsable;
	}

	public void setIsUsable(String isUsable) {
		this.isUsable = isUsable;
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

	@Override
	public String toString() {
		return "SyncDirectory [syncDirectoryId=" + syncDirectoryId
				+ ", syncFlag=" + syncFlag + ", brandId=" + brandId
				+ ", seriesId=" + seriesId + ", singleId=" + singleId
				+ ", isUsable=" + isUsable + ", createDate=" + createDate
				+ ", createBy=" + createBy + "]";
	}

	private static final long serialVersionUID = 5824847284317708198L;

}
