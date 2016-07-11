package com.cat.manage.user.controller;


import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.common.exception.ParameterException;
import com.cat.manage.common.model.Srm;
import com.cat.manage.common.util.Md5Util;
import com.cat.manage.shiro.user.CurrentUser;
import com.cat.manage.user.domain.User;
import com.cat.manage.user.service.UserService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.common.base.Strings;
import com.google.common.io.Closer;

/**
 * @Description: 登录控制器
 * @author 
 * @date 2015年10月21日 下午3:22:50
 */
@Controller
@RequestMapping("/authc")
public class LoginController {
    
    private static final String REMEMBER_ME_ON = "1";
    
    private static final String USER_STATE_NORMAL = "0";
    
    private static final String USER_STATE_SUPER = "1";//特殊状态
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private Producer captchaProducer;
	
	/**
	 * 登录控制
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Srm login(String username, String password, String captcha, String rememberme, HttpServletRequest request) {
	    if (Strings.isNullOrEmpty(username))
	        throw new ParameterException("1", "用户名不能为空");
	    
	    if (Strings.isNullOrEmpty(password))
	        throw new ParameterException("1", "密码不能为空");
	    
	    if (Strings.isNullOrEmpty(captcha))
	        throw new ParameterException("1", "验证码不能为空");
	    
	    HttpSession session = request.getSession();
	    String leaveCaptcha = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
	    if (!captcha.equalsIgnoreCase(leaveCaptcha))
	        throw new ParameterException("1", "验证码错误");
	    
	    User user = userService.findUserByUsername(username);
	    if (user == null)
	        throw new ParameterException("1", "用户或密码不正确");
	    
	    if(!USER_STATE_NORMAL.equals(user.getState()) && !USER_STATE_SUPER.equals(user.getState()))
	    	throw new BusinessException("1", "用户状态不正常");
	    	
	    String salt      = user.getSalt();
	    String loginPass = Md5Util.digest(password + salt);
	   
	    Subject subject = SecurityUtils.getSubject();
	    UsernamePasswordToken token = new UsernamePasswordToken(username, loginPass);
	    token.setRememberMe(REMEMBER_ME_ON.equals(rememberme));
	    
	    Srm srm = new Srm();
	    try {
	        subject.login(token);
	        srm.setResultCode("0").setResultMessage("登录成功");
	        
	    } catch (Exception e) {
	        LOG.debug("", e);
	        srm.setResultCode("1").setResultMessage("用户名或密码不正确");
	    }
	    
	    return srm;
	}
	
	/**
	 * 退出控制 
	 */
	@RequestMapping("/logout")
	public @ResponseBody Srm logout() {
	    Subject subject = SecurityUtils.getSubject();
	    
	    if (subject.isAuthenticated()) {
	        LOG.debug("用户 [{}] 退出! ", (String)subject.getPrincipal());
	    }
	    
	    subject.logout();
	    return new Srm().setResultCode("0").setResultMessage("用户退出成功!");
	}

	/**
	 * 用户信息
	 */
	@RequestMapping("/userInfo")
	public @ResponseBody Srm onUserInfo(@CurrentUser User user) {
	    return new Srm().setResultCode("0").setResultMessage("查询用户信息成功 ").addResult(user);
	}
	
	@RequestMapping("/captcha")
	public void onCaptchaCode(HttpServletRequest request, HttpServletResponse response) {
        response.setDateHeader("Expires", 0);  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");  
        
        HttpSession session = request.getSession();
        String capText = captchaProducer.createText(); 
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
         
        Closer closer = Closer.create();
        try {  
            ServletOutputStream out = response.getOutputStream();
            closer.register(out);

            ImageIO.write(captchaProducer.createImage(capText), "jpg", out);
            out.flush();
            
        } catch (Exception e) {
            LOG.error("", e);
            
        } finally {  
            try {
                closer.close();
            } catch (IOException e) {
                LOG.error("", e);
            }
        }
	}
	
	private static final transient Logger LOG = LoggerFactory.getLogger(LoginController.class);
}
