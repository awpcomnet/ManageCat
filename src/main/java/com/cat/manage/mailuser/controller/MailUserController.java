package com.cat.manage.mailuser.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.cat.manage.mailuser.domain.MailUser;
import com.cat.manage.mailuser.service.MailUserService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 邮件用户控制
 * @author 王航
 * @date 2016年2月26日 上午11:23:33
 */
@RestController
@RequestMapping("/mailuser")
public class MailUserController {
	@Autowired
	private MailUserService mailUserService;
	
	@RequestMapping("/add")
	public Srm addMailUser(MailUser mailUser){
		mailUserService.addMailUser(mailUser);
		return new Srm().setResultCode("0").setResultMessage("添加邮件用户成功");
	}
	
	@RequestMapping("/delete")
	public Srm deleteMailUser(Integer id){
		mailUserService.deleteMailUser(id);
		return new Srm().setResultCode("0").setResultMessage("删除邮件用户成功");
	}
	
	@RequestMapping("/query")
	public Srm queryMailUserForPage(HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		int pageNumber = params.getInt("page");
		int pageSize = params.getInt("limit");
		PageInfo<MailUser> page = mailUserService.queryMailUser(pageNumber, pageSize);
		return new Srm().setResultCode("0").setResultMessage("查询邮件用户成功").buildPageInfo(page);
		
	}
}
