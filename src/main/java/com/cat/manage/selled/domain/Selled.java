package com.cat.manage.selled.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 售出清单领域模型
 * @author 王航
 * @date 2016年2月23日 上午10:28:28
 */
public class Selled implements Serializable{
	private Integer id;//售出清单子单唯一编号
	private Integer storeId;//入库清单唯一编号
	private Integer checkId;//下单清单唯一编号
	private Integer brandId;//品牌编号
	private Integer seriesId;//系列编号
	private Integer singleId;//单品编号
	private Integer sellNum;//售出数量
	private String unitPrice;//下单单价（美元单位）
	private String payby;//付款人(下单清单中的付款人)
	private Double unitRmb;//实际单价(人民币)
	private Double unitPostage;//实际单个邮费(人民币)
	private Double unitCost;//实际成本(人民币)
	private Double sellingPrice;//实际售价(人民币)
	private Double refund;//补损金额
	private String remark;//备注
	private String sellTime;//售出时间
	private String selledStatus;//售出状态(已售出，已售出（补损），已损坏)
	private Date createDate;//创建时间
	private Date updateDate;//更新时间
	
	private String createDateFormat;//格式化后的创建时间
	private String updateDateFormat;//格式化后的更新时间
	private String brandName;//品牌名称
	private String seriesName;//系列名称
	private String singleName;//单品名称
	private String sumPrice;//售出总金额
	private String brandEname;//品牌英文名称
	private String singleEname;//单品英文名称
	//新加字段 国外快递单号 主邮寄单号 批次号
	private String trackingNumber;//国外快递单号
	private String headTrackingNumber;//主邮寄单号
	private String batchNo;//批次号
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
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

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getPayby() {
		return payby;
	}

	public void setPayby(String payby) {
		this.payby = payby;
	}

	public Double getUnitRmb() {
		return unitRmb;
	}

	public void setUnitRmb(Double unitRmb) {
		this.unitRmb = unitRmb;
	}

	public Double getUnitPostage() {
		return unitPostage;
	}

	public void setUnitPostage(Double unitPostage) {
		this.unitPostage = unitPostage;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Double getRefund() {
		return refund;
	}

	public void setRefund(Double refund) {
		this.refund = refund;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSellTime() {
		return sellTime;
	}

	public void setSellTime(String sellTime) {
		this.sellTime = sellTime;
	}

	public String getSelledStatus() {
		return selledStatus;
	}

	public void setSelledStatus(String selledStatus) {
		this.selledStatus = selledStatus;
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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getSingleName() {
		return singleName;
	}

	public void setSingleName(String singleName) {
		this.singleName = singleName;
	}

	public String getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(String sumPrice) {
		this.sumPrice = sumPrice;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public String getBrandEname() {
		return brandEname;
	}

	public void setBrandEname(String brandEname) {
		this.brandEname = brandEname;
	}

	public String getSingleEname() {
		return singleEname;
	}

	public void setSingleEname(String singleEname) {
		this.singleEname = singleEname;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getHeadTrackingNumber() {
		return headTrackingNumber;
	}

	public void setHeadTrackingNumber(String headTrackingNumber) {
		this.headTrackingNumber = headTrackingNumber;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Override
	public String toString() {
		return "Selled [id=" + id + ", storeId=" + storeId + ", checkId="
				+ checkId + ", brandId=" + brandId + ", seriesId=" + seriesId
				+ ", singleId=" + singleId + ", sellNum=" + sellNum
				+ ", unitPrice=" + unitPrice + ", payby=" + payby
				+ ", unitRmb=" + unitRmb + ", unitPostage=" + unitPostage
				+ ", unitCost=" + unitCost + ", sellingPrice=" + sellingPrice
				+ ", refund=" + refund + ", remark=" + remark + ", sellTime="
				+ sellTime + ", selledStatus=" + selledStatus + ", createDate="
				+ createDate + ", updateDate=" + updateDate
				+ ", createDateFormat=" + createDateFormat
				+ ", updateDateFormat=" + updateDateFormat + ", brandName="
				+ brandName + ", seriesName=" + seriesName + ", singleName="
				+ singleName + ", sumPrice=" + sumPrice + "]";
	}

	private static final long serialVersionUID = -1434545964113251045L;

}
