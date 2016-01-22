package com.cat.manage.order.domain;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable{
	private Integer orderId;
	private String foreignState;//国外订单状态
	private String transfer;//转运状态
	private String affirmState;//确认收货状态
	private Date createDate; //创建时间
	private Date updateDate; //更新时间
	private String createDateFormat;//格式化后的时间
	private String updateDateFormat;//格式化后的更新时间
	
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



	private static final long serialVersionUID = 617094632310826077L;
}
