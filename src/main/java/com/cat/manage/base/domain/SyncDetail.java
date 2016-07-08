package com.cat.manage.base.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 单品/系列 同步详情 领域模型
 * @author wanghang
 *
 */
public class SyncDetail implements Serializable{
	private Integer syncId ;//同步表ID
	private String syncFlag ;//同步标志，用于记录同步目录表的标记
	private String orderType ;//订单类别 01-下单清单 02-邮寄清单 03-仓库清单 04-售出清单
	private Integer orderId ;//订单的唯一编号
	private Integer brandId ;//品牌ID
	private Integer seriesId ;//系列ID 
	private Integer singleId ;//单品ID
	private String isUsable ;//是否可用  0-否 1-是 (暂时不适用这个字段)
	private Date createDate ;//创建时间
	private String createBy ;//创建人
	
	public Integer getSyncId() {
		return syncId;
	}

	public void setSyncId(Integer syncId) {
		this.syncId = syncId;
	}

	public String getSyncFlag() {
		return syncFlag;
	}

	public void setSyncFlag(String syncFlag) {
		this.syncFlag = syncFlag;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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
		return "SyncDetail [syncId=" + syncId + ", syncFlag=" + syncFlag
				+ ", orderType=" + orderType + ", orderId=" + orderId
				+ ", brandId=" + brandId + ", seriesId=" + seriesId
				+ ", singleId=" + singleId + ", isUsable=" + isUsable
				+ ", createDate=" + createDate + ", createBy=" + createBy + "]";
	}

	private static final long serialVersionUID = -5037870052447777717L;

}
