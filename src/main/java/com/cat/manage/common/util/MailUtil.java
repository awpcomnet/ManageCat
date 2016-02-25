package com.cat.manage.common.util;

import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

/**
 * 
 * @Description: 邮件工具
 * @author 王航
 * @date 2016年2月25日 下午2:12:56
 */
@Repository
public class MailUtil {

	@Autowired
	private JavaMailSenderImpl javaMailSender;

	/**
	 * 发送邮件
	 * @param toMails 目标邮箱(多个以,隔开)
	 * @param subject 主题
	 * @param htmlContext 内容
	 * @throws MessagingException
	 */
	public void sendBaseMail(String toMails, String subject, String htmlContext)
			throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();

		InternetAddress[] toList = new InternetAddress().parse(toMails);
		message.setRecipients(Message.RecipientType.TO, toList);
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(javaMailSender.getUsername());
		helper.setSubject(subject);

//		String.format(
//				"Hi, %s:\n\t 你好！\n\t i have a question to ask you\n <p align=\"right\">%s</p>",
//				"laowang", "wanghang");
		
		BodyPart mdp = new MimeBodyPart();
		mdp.setContent(htmlContext, "text/html;charset=gb2312");
		Multipart mm = new MimeMultipart();
		mm.addBodyPart(mdp);
		message.setContent(mm);
		message.saveChanges();

		javaMailSender.send(message);
	}
	
}
