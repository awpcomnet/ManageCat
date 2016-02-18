package com.cat.manage.user.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.common.exception.ParameterException;
import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.cat.manage.common.util.Md5Util;
import com.cat.manage.shiro.user.CurrentUser;
import com.cat.manage.user.domain.User;
import com.cat.manage.user.service.UserService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @Description: 接受外部用户相关请求
 * @author 
 * @date 2015年10月20日 下午5:20:37
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/all")
	public @ResponseBody Srm getAllUsers() {
		List<User> users = userService.getAllUsers();
		return new Srm().setResultCode("0").setResultMessage("查询成功").addAll(users);
	}
	
	@RequestMapping("/add")
	public @ResponseBody Srm addUser(HttpServletRequest request, @CurrentUser User current) { 
	    /** 1, 获得参数并校验 */
	    HttpParams params = HttpParams.buildFrom(request);
	    Map<String, String> requestMap = params.getMap();
	    
	    String username = assertNotNullOrEmpty(requestMap, "username");
	    String realname = assertNotNullOrEmpty(requestMap, "realname");
	    String password = assertNotNullOrEmpty(requestMap, "password");
	    String repeatPa = assertNotNullOrEmpty(requestMap, "repeatPassword");
	    String userStat = assertNotNullOrEmpty(requestMap, "state");
	    
	    /** 2, 检查输入的密码是否相等 */
	    if (!repeatPa.equals(password)) {
	        throw new BusinessException("2", "两次输入的密码不相等");
	    }
	    
	    /** 3, 组装Bean 基本数据 */
	    User user = new User();
	    user.setUsername(username);
	    user.setRealname(realname);
	    user.setState(userStat);
	    
	    String salt = Md5Util.getRandomSalt();
	    user.setSalt(salt);
	    user.setPassword(Md5Util.digest(password + salt));
	    user.setCreator(current.getUsername());
	    
	    /** 4, 提交业务类处理 */
	    userService.addUser(user);

		return new Srm().setResultCode("0").setResultMessage("新增用户成功");
	}
	
	/**
	 * 获得参数, 顺便检查一下是否为空
	 */
	private String assertNotNullOrEmpty(Map<String, String> requestMap, String key) {
	    String value = requestMap.get(key);
	    if (Strings.isNullOrEmpty(value))
	        throw new ParameterException("1", "参数["+key+"]不能为空!");
	    
	    return value;
	}
	
	@RequestMapping("/update")
	public @ResponseBody Srm updateUser(HttpServletRequest request, @CurrentUser User current) {
	    /** 1, 获得参数并校验 */
        HttpParams params = HttpParams.buildFrom(request);
        Map<String, String> requestMap = params.getMap();
        
        int id = params.getInt("userId");
        String username = assertNotNullOrEmpty(requestMap, "username");
        String realname = assertNotNullOrEmpty(requestMap, "realname");
        String email = assertNotNullOrEmpty(requestMap, "email");
        String userStat = assertNotNullOrEmpty(requestMap, "state");
        String depts    = requestMap.get("departments");
        String roles    = requestMap.get("roles");
        
        /** 2, 组装User模型 */
        User user = new User();
        user.setUserId(id);
        user.setUsername(username);
        user.setRealname(realname);
        user.setEmail(email);
        user.setState(userStat);
        
        /** 3, 更新行为信息 */
        userService.updateUser(user, depts, roles);
        
	    return new Srm().setResultCode("0").setResultMessage("更新用户数据成功");
	}
	
	
	@RequestMapping("/queryAll")
	public @ResponseBody Srm getAllUsersForStore() {
		List<User> users = userService.getAllUsers();
		List<Map<String, Object>> list = Lists.newArrayList();
		for(User u : users){
			Map<String, Object> map = Maps.newHashMap();
			map.put("userId", u.getUserId());
			map.put("name", u.getUsername());
			
			list.add(map);
		}
		return new Srm().setResultCode("0").setResultMessage("查询成功").addAll(list);
	}
	
	private static final transient Logger LOG = LoggerFactory.getLogger(UserController.class);
}
                                       
