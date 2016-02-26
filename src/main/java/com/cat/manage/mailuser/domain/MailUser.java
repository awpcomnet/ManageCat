package com.cat.manage.mailuser.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮件用户
 * @Description: 
 * @author 王航
 * @date 2016年2月26日 上午11:07:53
 */
public class MailUser implements Serializable{
	private Integer id;
	private String username;
	private String arg0;
	private Date createDate;
	private String createDateFormat;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getArg0() {
		return arg0;
	}

	public void setArg0(String arg0) {
		this.arg0 = arg0;
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
		return "MailUser [id=" + id + ", username=" + username + ", arg0="
				+ arg0 + ", createDate=" + createDate + "]";
	}

	private static final long serialVersionUID = 6961192621057903135L;

}
