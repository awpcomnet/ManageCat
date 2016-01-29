package com.cat.manage.selledstore.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 售出仓库领域模型
 * @author wanghang
 *
 */
public class SelledStore implements Serializable{
	private Integer storeId;//仓库存货编号
	private Integer superOrderId;//主订单ID
	private Integer suborderId;//子订单编号
	private Integer brandId;//品牌编号
	private Integer seriesId;//系列编号
	private Integer singleId;//单品编号
	private Integer num;//数量
	private Double orderPrice;//下单单价(单个)
	private Double transferPrice;//运费(单个)
	private Double costPrice;//成本价(单个)
	private Double sellingPrice;//售价(单个)
	private Date createDate;//创建时间
	private Date updateDate;//更新时间
	private String createDateFormat;//格式化后的创建时间
	private String updateDateFormat;//格式化后的更新时间
	
	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getSuperOrderId() {
		return superOrderId;
	}

	public void setSuperOrderId(Integer superOrderId) {
		this.superOrderId = superOrderId;
	}

	public Integer getSuborderId() {
		return suborderId;
	}

	public void setSuborderId(Integer suborderId) {
		this.suborderId = suborderId;
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

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Double getTransferPrice() {
		return transferPrice;
	}

	public void setTransferPrice(Double transferPrice) {
		this.transferPrice = transferPrice;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
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
		return "SelledStore [storeId=" + storeId + ", superOrderId="
				+ superOrderId + ", suborderId=" + suborderId + ", brandId="
				+ brandId + ", seriesId=" + seriesId + ", singleId=" + singleId
				+ ", num=" + num + ", orderPrice=" + orderPrice
				+ ", transferPrice=" + transferPrice + ", costPrice="
				+ costPrice + ", sellingPrice=" + sellingPrice
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", createDateFormat=" + createDateFormat
				+ ", updateDateFormat=" + updateDateFormat + "]";
	}

	private static final long serialVersionUID = -1521080327633222263L;

}
