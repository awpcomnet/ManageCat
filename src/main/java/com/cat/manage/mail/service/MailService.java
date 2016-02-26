package com.cat.manage.mail.service;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.common.util.MailUtil;
import com.cat.manage.mailuser.domain.MailUser;
import com.cat.manage.mailuser.service.MailUserService;

/**
 * @Description: 邮件服务类
 * @author 王航
 * @date 2016年2月25日 下午4:31:46
 */
@Service
public class MailService {
	
	@Autowired
	private MailUtil mailUtil;
	
	@Autowired
	private MailUserService mailUserService;
	
	/**
	 * 发送提示邮件
	 */
	public void sendNoticeMail(String toMails){
		DateTime now = new DateTime(new Date());
		now = now.plusMonths(-1);
		
		String subject = "["+now.getMonthOfYear()+"月]收益提醒";
		String htmlContext = "<p>Hi!</p><p>&nbsp;&nbsp;&nbsp;您好！经过辛苦劳作之后，现在到了收获的时候了，快去导出收益详情看一下吧！</p><br><p align='right'>lazyCatManage</p>";
		
		try {
			mailUtil.sendBaseMail(toMails, subject, htmlContext);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送提示邮件
	 * 默认存储用户
	 */
	public void sendNoticeMail(){
		List<MailUser> mailUserList = mailUserService.queryMailUserForList();
		if(mailUserList == null){
			return;
		}
		StringBuffer toMails = new StringBuffer();
		for(int i=0, len=mailUserList.size(); i<len; i++){
			MailUser mailUser = mailUserList.get(i);
			toMails.append(mailUser.getUsername());
			if(i != len - 1)
				toMails.append(",");
		}
		
		DateTime now = new DateTime(new Date());
		now = now.plusMonths(-1);
		
		String subject = "["+now.getMonthOfYear()+"月]收益提醒";
		String htmlContext = "<p>Hi!</p><p>&nbsp;&nbsp;&nbsp;您好！经过辛苦劳作之后，现在到了收获的时候了，快去导出收益详情看一下吧！</p><br><p align='right'>lazyCatManage</p>";
		
		try {
			mailUtil.sendBaseMail(toMails.toString(), subject, htmlContext);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
