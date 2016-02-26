package com.cat.manage.base.domain;

import java.io.Serializable;
import java.util.Date;

public class MailSet implements Serializable{
	
	private Integer id;
	private String host;
	private Integer port;
	private String username;
	private String password;
	private String arg0;
	private Date createDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	@Override
	public String toString() {
		return "MailSet [id=" + id + ", host=" + host + ", port=" + port
				+ ", username=" + username + ", password=" + password
				+ ", arg0=" + arg0 + ", createDate=" + createDate + "]";
	}
	private static final long serialVersionUID = 1L;
}
