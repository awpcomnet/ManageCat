package com.cat.manage.order.domain;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable{
	private Integer orderId;
	private String foreignState;//国外订单状态
	private String transfer;//转运状态
	private String affirmState;//确认收货状态
	private String remark;//备注
	private String payby;//付款人
	private Date createDate; //创建时间
	private Date updateDate; //更新时间
	/*非数据库字段*/
	private String createDateFormat;//格式化后的时间
	private String updateDateFormat;//格式化后的更新时间
	private String sumOrderPrice; //下单总价
	private String sumTransferPrice; //运费总价
	private String sumCostPrice; //成本价总价
	private String sumSellingPrice; //售价(总价)
	private String sumSubOrderNum;//总子订单数
	
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

	public Integer getOrderId() {
		return orderId;
	}


	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}


	public String getForeignState() {
		return foreignState;
	}


	public void setForeignState(String foreignState) {
		this.foreignState = foreignState;
	}


	public String getTransfer() {
		return transfer;
	}


	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}


	public String getAffirmState() {
		return affirmState;
	}


	public void setAffirmState(String affirmState) {
		this.affirmState = affirmState;
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

	public String getSumSubOrderNum() {
		return sumSubOrderNum;
	}

	public void setSumSubOrderNum(String sumSubOrderNum) {
		this.sumSubOrderNum = sumSubOrderNum;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", foreignState=" + foreignState
				+ ", transfer=" + transfer + ", affirmState=" + affirmState
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", createDateFormat=" + createDateFormat
				+ ", updateDateFormat=" + updateDateFormat + ", sumOrderPrice="
				+ sumOrderPrice + ", sumTransferPrice=" + sumTransferPrice
				+ ", sumCostPrice=" + sumCostPrice + ", sumSellingPrice="
				+ sumSellingPrice + ", sumSubOrderNum=" + sumSubOrderNum + "]";
	}

	private static final long serialVersionUID = 617094632310826077L;
}
