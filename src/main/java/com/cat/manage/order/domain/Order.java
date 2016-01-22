package com.cat.manage.order.domain;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable{
	private Integer orderId;
	private String foreignState;//国外订单状态
	private String transfer;//转运状态
	private String affirmState;//确认收货状态
	private String createDate; //创建时间
	private String updateDate; //更新时间
	
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



	private static final long serialVersionUID = 617094632310826077L;
}
