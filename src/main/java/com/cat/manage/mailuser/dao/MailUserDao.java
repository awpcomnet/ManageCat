package com.cat.manage.mailuser.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.mailuser.domain.MailUser;

/**
 * 邮件用户dao
 * @Description: 
 * @author 王航
 * @date 2016年2月26日 上午11:10:07
 */
@Repository
public interface MailUserDao {
	/**
	 * 添加邮件用户
	 * @param mailUser
	 */
	public void addMailUser(MailUser mailUser);
	
	/**
	 * 删除邮件用户
	 * @param id
	 */
	public void deleteMailUser(Integer id);
	
	/**
	 * 查询邮件用户
	 * @return
	 */
	public List<MailUser> queryMailUser();
}
