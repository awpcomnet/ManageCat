package com.cat.manage.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.base.dao.MailSetDao;
import com.cat.manage.base.domain.MailSet;

/**
 * 邮件设置服务
 * @Description: 
 * @author 王航
 * @date 2016年2月26日 上午10:40:37
 */
@Service
public class MailSetService {
	@Autowired
	private MailSetDao mailSetDao;
	
	public MailSet queryMailSet(){
		return mailSetDao.queryMailSet();
	}
}
