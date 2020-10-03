package com.lmy.eblog.shiro;
/**
 * @Project eblog
 * @Package com.lmy.eblog.shiro
 * @author Administrator
 * @date 2020/10/3 20:31
 * @version V1.0
 */

import com.lmy.eblog.service.MUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Lmy
 * @ClassName AccountRealm
 * @Description Shiro登录逻辑
 * @date 2020/10/3 20:31
 **/
@Component
public class AccountRealm extends AuthorizingRealm {


    @Autowired
    private MUserService mUserServiceImpl;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 验证逻辑
        UserInfo userInfo = mUserServiceImpl.login(token.getUsername(), String.valueOf(token.getPassword()));

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userInfo, token.getCredentials(), getName());
        return authenticationInfo;
    }
}
