package com.cat.manage.shiro.realm;

import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.manage.user.service.UserService;
import com.google.common.base.Strings;

public class MyAuthorizerFilter extends AuthorizationFilter{
	
	@Autowired
	private UserService userService;

	@Override
	protected boolean isAccessAllowed(ServletRequest request,ServletResponse response, Object mappedValue) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		String userName = (String) subject.getPrincipal();
		if(Strings.isNullOrEmpty(userName))
			return false;
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String uri = httpServletRequest.getRequestURI();
		String[] params = uri.split("/");
		String requestRole = params[1].trim()+":"+params[2].trim();
		
		//根据用户名查询用户拥有的权限
		Set<String> set = userService.findPermissionByUsername(userName);
		for(String role : set){
			System.out.println("拥有权限["+role+"]验证权限["+requestRole+"]");
			if(role.equals(requestRole))
				System.out.println("权限验证通过!");
		}
		
		System.out.println("权限验证失败!");
		return true;
	}

}
