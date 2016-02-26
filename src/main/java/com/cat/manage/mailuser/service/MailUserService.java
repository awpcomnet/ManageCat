package com.cat.manage.mailuser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.mailuser.dao.MailUserDao;
import com.cat.manage.mailuser.domain.MailUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 邮件用户服务
 * @Description: 
 * @author 王航
 * @date 2016年2月26日 上午11:18:03
 */
@Service
public class MailUserService {
	@Autowired
	private MailUserDao mailUserDao;
	
	/**
	 * 添加邮件用户信息
	 * @param mailUser
	 */
	public void addMailUser(MailUser mailUser){
		mailUserDao.addMailUser(mailUser);
	}
	
	/**
	 * 删除邮件用户信息
	 * @param id
	 */
	public void deleteMailUser(Integer id){
		mailUserDao.deleteMailUser(id);
	}
	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageInfo<MailUser> queryMailUser(int pageNumber, int pageSize){
		PageHelper.startPage(pageNumber, pageSize);
		List<MailUser> list = mailUserDao.queryMailUser();
		PageInfo<MailUser> page = new PageInfo<MailUser>(list);
		return page;
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<MailUser> queryMailUserForList(){
		return mailUserDao.queryMailUser();
	}
}
