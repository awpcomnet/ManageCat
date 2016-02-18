package com.cat.manage.user.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 管理系统用户模型
 * @author 
 * @date 2015年10月20日 下午3:20:04
 */
public class User implements Serializable {

	private int userId;                     // ID
	private String username;                // 用户名
	private String realname;                // 真实名
	private String password;                // 密码
	private String salt;                    // MD5 盐
	private String email;					// 邮箱
	private String state;                   // 用户状态
	private int loginTimes;                 // 用户登录次数
	private Date lastLoginDate;        		// 最后登录日期
	private Date createDate;           		// 创建日期
	private String creator;                 // 创建者
	private Date modifyDate;           		// 修改日期
	private String modifier;                	// 修改者
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username
				+ ", realname=" + realname + ", password=" + password
				+ ", salt=" + salt + ", email=" + email + ", state=" + state
				+ ", loginTimes=" + loginTimes + ", lastLoginDate="
				+ lastLoginDate + ", createDate=" + createDate + ", creator="
				+ creator + ", modifyDate=" + modifyDate + ", modifier="
				+ modifier + "]";
	}

	private static final long serialVersionUID = -1746098060592678313L;
}
