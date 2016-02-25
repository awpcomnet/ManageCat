package com.cat.manage.mail.service;

import java.util.Date;

import javax.mail.MessagingException;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.common.util.MailUtil;

/**
 * @Description: 邮件服务类
 * @author 王航
 * @date 2016年2月25日 下午4:31:46
 */
@Service
public class MailService {
	
	@Autowired
	private MailUtil mailUtil;
	
	/**
	 * 发送提示邮件
	 */
	public void sendNoticeMail(String toMails){
		DateTime now = new DateTime(new Date());
		
		String subject = "["+now.getMonthOfYear()+"月]收益提醒";
		String htmlContext = "<p>Hi!</p><p>&nbsp;&nbsp;&nbsp;您好！经过辛苦劳作之后，现在到了收获的时候了，快去导出收益详情看一下吧！</p><br><p align='right'>lazyCatManage</p>";
		
		try {
			mailUtil.sendBaseMail(toMails, subject, htmlContext);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
