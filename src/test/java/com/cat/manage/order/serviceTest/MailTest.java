package com.cat.manage.order.serviceTest;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cat.manage.mail.service.MailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MailTest {
	
	@Autowired
	private JavaMailSenderImpl javaMailSender;
	
	@Autowired
	private MailService mailService;
	
	@Test
	public void testMail() throws MessagingException{
		String toMails = "aa@163.com";
		mailService.sendNoticeMail(toMails);
		
		
		
//		MimeMessage message = javaMailSender.createMimeMessage();
//		
//		MimeMessageHelper helper = new MimeMessageHelper(message, true);
//		
//		String to="1048287135@qq.com,1044652158@qq.com";
//		InternetAddress[] toList = new InternetAddress().parse(to);
//		message.setRecipients(Message.RecipientType.TO, toList);
//		
//		helper.setFrom("awpcomnet@qq.com");
////		helper.setTo("1048287135@qq.com");
//		helper.setSubject("测试咯");
//		//helper.setText(String.format("Hi, %s:\n\t 你好！\n\t i have a question to ask you\n <p align=\"right\">%s</p>", "laowang", "wanghang"));
//		
//		 BodyPart mdp = new MimeBodyPart();   
//         mdp.setContent(String.format("Hi, %s:\n\t 你好！\n\t i have a question to ask you\n <p align=\"right\">%s</p>", "laowang", "wanghang"), "text/html;charset=gb2312");   
//         Multipart mm = new MimeMultipart();   
//         mm.addBodyPart(mdp);   
//         message.setContent(mm);   
//         message.saveChanges();   
//		
//		javaMailSender.send(message);
		
		
	}
	
	

}
