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
	private Double orderPrice; //下单单价
	private Double transferPrice; //运费(平均)
	private Double costPrice; //成本价(单个)
	private Double sellingPrice; //售价(单个)
	private String curState;//当前状态(0:国外下单成功 1:国外已砍单 2:已到转运公司 3:转运中 4:已到海关 5:已入库 6:售出 99:损坏)
	private String createDate; //创建时间
	private String updateDate; //更新时间
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
	
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	@Override
	public String toString() {
		return "SubOrder [suborderId=" + suborderId + ", superOrderId="
				+ superOrderId + ", singleId=" + singleId + ", num=" + num
				+ ", orderPrice=" + orderPrice + ", transferPrice="
				+ transferPrice + ", costPrice=" + costPrice
				+ ", sellingPrice=" + sellingPrice + ", curState=" + curState
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ "]";
	}

	private static final long serialVersionUID = -471158871458947793L;
}
