package com.cat.manage.order.domain;

import java.io.Serializable;
import java.util.Date;

public class SubOrder implements Serializable{
	private Integer suborderId; //子订单ID
	private Integer superOrderId; //主订单ID
	private Integer brandId; //品牌编号
	private Integer seriesId; //系列编号
	private Integer singleId; //单品编号
	private Integer num; //数量
	private Integer sellNum;//销售数量
	private Double orderPrice; //下单单价
	private Double transferPrice; //运费(平均)
	private Double costPrice; //成本价(单个)
	private Double sellingPrice; //售价(单个)
	private String curState;//当前状态(0:国外下单成功 1:国外已砍单 2:已到转运公司 3:转运中 4:已到海关 5:已入库 6:售出 99:损坏)
	private String remark;//备注
	private String payby;//付款人
	private Date createDate; //创建时间
	private Date updateDate; //更新时间
	private String createDateFormat;//格式化后的创建时间
	private String updateDateFormat;//格式化后的更新时间
	private String brandName;//品牌名称
	private String seriesName;//系列名称
	private String singleName;//单品名称
	/*非数据库字段*/
	private String sumOrderPrice; //下单总价
	private String sumTransferPrice; //运费总价
	private String sumCostPrice; //成本价总价
	private String sumSellingPrice; //售价(总价)
	
	public String getPayby() {
		return payby;
	}
	public void setPayby(String payby) {
		this.payby = payby;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getSuborderId() {
		return suborderId;
	}
	public void setSuborderId(Integer suborderId) {
		this.suborderId = suborderId;
	}
	public Integer getSuperOrderId() {
		return superOrderId;
	}
	public void setSuperOrderId(Integer superOrderId) {
		this.superOrderId = superOrderId;
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
	public String getCurState() {
		return curState;
	}
	public void setCurState(String curState) {
		this.curState = curState;
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
	public String getSumOrderPrice() {
		return sumOrderPrice;
	}
	public void setSumOrderPrice(String sumOrderPrice) {
		this.sumOrderPrice = sumOrderPrice;
	}
	public String getSumTransferPrice() {
		return sumTransferPrice;
	}
	public void setSumTransferPrice(String sumTransferPrice) {
		this.sumTransferPrice = sumTransferPrice;
	}
	public String getSumCostPrice() {
		return sumCostPrice;
	}
	public void setSumCostPrice(String sumCostPrice) {
		this.sumCostPrice = sumCostPrice;
	}
	public String getSumSellingPrice() {
		return sumSellingPrice;
	}
	public void setSumSellingPrice(String sumSellingPrice) {
		this.sumSellingPrice = sumSellingPrice;
	}
	public Integer getSellNum() {
		return sellNum;
	}
	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}
	@Override
	public String toString() {
		return "SubOrder [suborderId=" + suborderId + ", superOrderId="
				+ superOrderId + ", brandId=" + brandId + ", seriesId="
				+ seriesId + ", singleId=" + singleId + ", num=" + num
				+ ", sellNum=" + sellNum + ", orderPrice=" + orderPrice
				+ ", transferPrice=" + transferPrice + ", costPrice="
				+ costPrice + ", sellingPrice=" + sellingPrice + ", curState="
				+ curState + ", createDate=" + createDate + ", updateDate="
				+ updateDate + ", createDateFormat=" + createDateFormat
				+ ", updateDateFormat=" + updateDateFormat + ", brandName="
				+ brandName + ", seriesName=" + seriesName + ", singleName="
				+ singleName + ", sumOrderPrice=" + sumOrderPrice
				+ ", sumTransferPrice=" + sumTransferPrice + ", sumCostPrice="
				+ sumCostPrice + ", sumSellingPrice=" + sumSellingPrice + "]";
	}

	private static final long serialVersionUID = -471158871458947793L;
}
