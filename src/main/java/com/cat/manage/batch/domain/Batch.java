package com.cat.manage.batch.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 批次号管理
 * @author wanghang
 *
 */
public class Batch implements Serializable{
	private Integer id;//唯一编号
	private String batchNo;//批次号
	private Date createDate;//创建时间
	
	private Integer orderNum;//订单条数
	private String sumPrice;//总金额($)
	private String createDateFormat;//格式化后的创建时间
	
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(String sumPrice) {
		this.sumPrice = sumPrice;
	}

	public String getCreateDateFormat() {
		return createDateFormat;
	}

	public void setCreateDateFormat(String createDateFormat) {
		this.createDateFormat = createDateFormat;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Batch [id=" + id + ", batchNo=" + batchNo + ", createDate="
				+ createDate + ", orderNum=" + orderNum + ", sumPrice="
				+ sumPrice + ", createDateFormat=" + createDateFormat + "]";
	}

	private static final long serialVersionUID = -6892063195108841270L;

}
