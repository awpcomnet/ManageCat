package com.cat.manage.base.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 系列历史记录领域模型
 * @author wanghang
 *
 */
public class SeriesHistory implements Serializable{
	private Integer seriesHisId;//系列历史表ID
	private Integer seriesId;//系列ID
	private String seriesName;//系列名称
	private String seriesEname;//系列英文名称
	private String ofOrigin;//所属品牌
	private String isUse;//是否使用  0-否 1-是
	private Date createDate;//创建时间
	private String createBy;//创建人
	private Date updateDate;//更新时间
	private String updateBy;//更新人
	private String needSynchronization;//同步标志   已经不再使用
	
	public Integer getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getSeriesEname() {
		return seriesEname;
	}

	public void setSeriesEname(String seriesEname) {
		this.seriesEname = seriesEname;
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

	public Integer getSeriesHisId() {
		return seriesHisId;
	}

	public void setSeriesHisId(Integer seriesHisId) {
		this.seriesHisId = seriesHisId;
	}

	public String getNeedSynchronization() {
		return needSynchronization;
	}

	public void setNeedSynchronization(String needSynchronization) {
		this.needSynchronization = needSynchronization;
	}

	@Override
	public String toString() {
		return "SeriesHistory [seriesHisId=" + seriesHisId + ", seriesId="
				+ seriesId + ", seriesName=" + seriesName + ", seriesEname="
				+ seriesEname + ", ofOrigin=" + ofOrigin + ", isUse=" + isUse
				+ ", createDate=" + createDate + ", createBy=" + createBy
				+ ", updateDate=" + updateDate + ", updateBy=" + updateBy
				+ ", needSynchronization=" + needSynchronization + "]";
	}

	private static final long serialVersionUID = 7346383716508049128L;
}
