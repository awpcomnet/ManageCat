package com.cat.manage.shiro.realm;

import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import jxl.common.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.manage.common.model.Srm;
import com.cat.manage.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

public class MyAuthorizerFilter extends AuthorizationFilter{
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private UserService userService;

	@Override
	protected boolean isAccessAllowed(ServletRequest request,ServletResponse response, Object mappedValue) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		String userName = (String) subject.getPrincipal();
		if(Strings.isNullOrEmpty(userName)){
			return false;
		}
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String uri = httpServletRequest.getRequestURI();
		String[] params = uri.split("/");
		String requestRole = "";
		for(int i=0,len=params.length; i<len; i++){
			requestRole += params[i].trim();
			if(i != len -1 && i != 0)
				requestRole += ":";
			
		}
		
		//根据用户名查询用户拥有的权限
		Set<String> set = userService.findPermissionByUsername(userName);
		for(String role : set){
			LOG.info("["+userName+"]拥有权限["+role+"]验证权限["+requestRole+"]");
			if(role.equals(requestRole)){
				LOG.info("权限验证通过!");
				return true;
			}
		}
		
		LOG.info("权限验证失败!");
		if(userName.equals("admin"))
			return true;
		return false;
	}

	@Override
	public boolean onPreHandle(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		if(!isAccessAllowed(request, response, null)){
			System.out.println("enter contrll ***********");
			PrintWriter out = new PrintWriter(response.getOutputStream());
			try {
				Srm srm = new Srm().setResultCode("3").setResultMessage("无权访问");
				out.write(mapper.writeValueAsString(srm));
				out.flush();
				
			} catch (Exception e) {
				
			} finally {
				if (out != null) {
					out.close();
				}
			}
			
			return false;
		}
		return true;
	}

	private static Logger LOG = Logger.getLogger(MyAuthorizerFilter.class);
}
