package com.cat.manage.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.manage.user.domain.User;
import com.cat.manage.user.service.UserService;

/**
 * @Description: 安全数据源
 * @author 
 * @date 2015年11月30日 下午1:53:48
 */
public class CustomRealm extends AuthorizingRealm {
    
    @Autowired
    private UserService userService;

    /**
     * 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal(); 
        
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.findRolesByUsername(username));
        authorizationInfo.setStringPermissions(userService.findPermissionByUsername(username));
        
        return authorizationInfo;
    }

    /**
     * 认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        User user = userService.findUserByUsername(username);
        
        if (user == null) {
            throw new UnknownAccountException("用户 [" + username + "] 不存在");
        }
        
        return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
    }
}
