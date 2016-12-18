package com.cat.manage.store.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 仓库领域模型
 * @author wanghang
 *
 */
public class Store implements Serializable{
	private Integer id;//入库清单子单唯一编号
	private Integer shippedId;//邮寄清单唯一编号
	private Integer checkId;//下单清单唯一编号
	private String trackingNumber;//国外快递单号
	private String transferCompany;//转运公司
	private Integer brandId;//品牌编号
	private Integer seriesId;//系列编号
	private Integer singleId;//单品编号
	private Integer num;//数量
	private String unitPrice;//下单单价（美元单位）
	private String payby;//付款人
	private String unitRmb;//实际单价(人民币)
	private String unitPostage;//实际单个邮费(人民币)
	private String unitCost;//实际成本(人民币)
	private String remark;//备注
	private Integer sellNum;//售出数量
	private String storeTime;//入库时间
	private String storeStatus;//仓库状态
	private Date createDate;//创建时间
	private Date updateDate;//更新时间
	
	private String createDateFormat;//格式化后的创建时间
	private String updateDateFormat;//格式化后的更新时间
	private String brandName;//品牌名称
	private String seriesName;//系列名称
	private String singleName;//单品名称
	private Integer residueNum;//剩余数量
	private String brandEname;//品牌名称
	private String singleEname;//单品名称
	private String headTrackingNumber;//主邮寄单号
	private String batchNo;//批次号
	private String planSellPrice;//预计售价
	private String specification;//规格
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShippedId() {
		return shippedId;
	}

	public void setShippedId(Integer shippedId) {
		this.shippedId = shippedId;
	}

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getTransferCompany() {
		return transferCompany;
	}

	public void setTransferCompany(String transferCompany) {
		this.transferCompany = transferCompany;
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

	public String getUnitRmb() {
		return unitRmb;
	}

	public void setUnitRmb(String unitRmb) {
		this.unitRmb = unitRmb;
	}

	public String getUnitPostage() {
		return unitPostage;
	}

	public void setUnitPostage(String unitPostage) {
		this.unitPostage = unitPostage;
	}

	public String getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public String getStoreTime() {
		return storeTime;
	}

	public void setStoreTime(String storeTime) {
		this.storeTime = storeTime;
	}

	public String getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(String storeStatus) {
		this.storeStatus = storeStatus;
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

	public Integer getResidueNum() {
		return residueNum;
	}

	public void setResidueNum(Integer residueNum) {
		this.residueNum = residueNum;
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

	public String getPlanSellPrice() {
		return planSellPrice;
	}

	public void setPlanSellPrice(String planSellPrice) {
		this.planSellPrice = planSellPrice;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Override
	public String toString() {
		return "Store [id=" + id + ", shippedId=" + shippedId + ", checkId="
				+ checkId + ", trackingNumber=" + trackingNumber
				+ ", transferCompany=" + transferCompany + ", brandId="
				+ brandId + ", seriesId=" + seriesId + ", singleId=" + singleId
				+ ", num=" + num + ", unitPrice=" + unitPrice + ", payby="
				+ payby + ", unitRmb=" + unitRmb + ", unitPostage="
				+ unitPostage + ", unitCost=" + unitCost + ", remark=" + remark
				+ ", sellNum=" + sellNum + ", storeTime=" + storeTime
				+ ", storeStatus=" + storeStatus + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", createDateFormat="
				+ createDateFormat + ", updateDateFormat=" + updateDateFormat
				+ ", brandName=" + brandName + ", seriesName=" + seriesName
				+ ", singleName=" + singleName + "]";
	}

	private static final long serialVersionUID = 8780174321869563798L;

}
