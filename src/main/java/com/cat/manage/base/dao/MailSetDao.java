package com.cat.manage.base.dao;

import org.springframework.stereotype.Repository;

import com.cat.manage.base.domain.MailSet;

/**
 * 邮件设置
 * @Description: 
 * @author 王航
 * @date 2016年2月26日 上午10:31:04
 */
@Repository
public interface MailSetDao {
	
	public MailSet queryMailSet();
}
