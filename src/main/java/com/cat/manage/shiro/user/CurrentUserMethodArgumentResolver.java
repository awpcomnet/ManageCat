package com.cat.manage.shiro.user;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cat.manage.user.domain.User;
import com.cat.manage.user.service.UserService;

/**
 * @Description: 注解解析器
 * @author 汪博
 * @date 2015年12月4日 上午10:55:37
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;
    
    public CurrentUserMethodArgumentResolver() {
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        
        Subject subject = SecurityUtils.getSubject();
        String username = (String)subject.getPrincipals().getPrimaryPrincipal();
        User user = userService.findUserByUsername(username);
        return user;
    }
}