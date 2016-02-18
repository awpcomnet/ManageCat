package com.cat.manage.shipped.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮寄清单领域模型(主单)
 * @author wanghang
 *
 */
public class ShippedHead implements Serializable{
	private Integer id;//邮寄清单主单唯一编号
	private String trackingNumber;//国外快递单号
	private String transferCompany;//转运公司
	private String submitTime;//提交邮寄时间
	private Double postage;//邮费
	private Date createDate;//创建时间
	private String createDateFormat;//格式化后的创建时间
	
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

	public String getTransferCompany() {
		return transferCompany;
	}

	public void setTransferCompany(String transferCompany) {
		this.transferCompany = transferCompany;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public Double getPostage() {
		return postage;
	}

	public void setPostage(Double postage) {
		this.postage = postage;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateDateFormat() {
		return createDateFormat;
	}

	public void setCreateDateFormat(String createDateFormat) {
		this.createDateFormat = createDateFormat;
	}

	@Override
	public String toString() {
		return "ShippedHead [id=" + id + ", trackingNumber=" + trackingNumber
				+ ", transferCompany=" + transferCompany + ", submitTime="
				+ submitTime + ", postage=" + postage + ", createDate="
				+ createDate + ", createDateFormat=" + createDateFormat + "]";
	}

	private static final long serialVersionUID = 2359690164704798550L;

}
