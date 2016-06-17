package com.cat.manage.check.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 下单清单模型
 * @author wanghang
 *
 */
public class Check implements Serializable{
	private Integer id;//下单清单唯一编号
	private String trackingNumber;//国外快递单号
	private String orderTime;//下单时间yyyyMMdd
	private String transferCompany;//转运公司
	private String orderAddr;//下单网站
	private Integer brandId;//品牌编号
	private Integer seriesId;//系列编号
	private Integer singleId;//单品编号
	private Integer num;//数量
	private Double unitPrice;//下单单价（美元单位）
	private String remark;//备注
	private String payby;//付款人
	private String orderStatus;//订单状态
	private Date createDate;//创建时间
	private Date updateDate;//更新时间
	private String batchNo;//批次号
	
	private String createDateFormat;//格式化后的创建时间
	private String updateDateFormat;//格式化后的更新时间
	private String sumPrice;//总价
	private String brandName;//品牌名称
	private String seriesName;//系列名称
	private String singleName;//单品名称
	private String brandEname;//品牌英文名
	private String singleEname;//单品英文名
	
	private String transferCompanyName;//转运公司
	private String orderAddrName;//下单网站
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getTransferCompany() {
		return transferCompany;
	}

	public void setTransferCompany(String transferCompany) {
		this.transferCompany = transferCompany;
	}

	public String getOrderAddr() {
		return orderAddr;
	}

	public void setOrderAddr(String orderAddr) {
		this.orderAddr = orderAddr;
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

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPayby() {
		return payby;
	}

	public void setPayby(String payby) {
		this.payby = payby;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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

	public String getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(String sumPrice) {
		this.sumPrice = sumPrice;
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

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	public String getTransferCompanyName() {
		return transferCompanyName;
	}

	public void setTransferCompanyName(String transferCompanyName) {
		this.transferCompanyName = transferCompanyName;
	}

	public String getOrderAddrName() {
		return orderAddrName;
	}

	public void setOrderAddrName(String orderAddrName) {
		this.orderAddrName = orderAddrName;
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

	@Override
	public String toString() {
		return "Check [id=" + id + ", trackingNumber=" + trackingNumber
				+ ", orderTime=" + orderTime + ", transferCompany="
				+ transferCompany + ", orderAddr=" + orderAddr + ", brandId="
				+ brandId + ", seriesId=" + seriesId + ", singleId=" + singleId
				+ ", num=" + num + ", unitPrice=" + unitPrice + ", remark="
				+ remark + ", payby=" + payby + ", orderStatus=" + orderStatus
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", batchNo=" + batchNo + ", createDateFormat="
				+ createDateFormat + ", updateDateFormat=" + updateDateFormat
				+ ", sumPrice=" + sumPrice + ", brandName=" + brandName
				+ ", seriesName=" + seriesName + ", singleName=" + singleName
				+ "]";
	}

	private static final long serialVersionUID = -935729869280274039L;

}
