package com.lmy.eblog.extension.shiro;
/**
 * @Project eblog
 * @Package com.lmy.eblog.extension.shiro
 * @author Administrator
 * @date 2020/10/3 20:31
 * @version V1.0
 */

import com.lmy.eblog.service.MUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Lmy
 * @ClassName AccountRealm
 * @Description Shiro登录逻辑
 * @date 2020/10/3 20:31
 **/
@Component
public class AccountRealm extends AuthorizingRealm {


    @Resource
    private MUserService mUserServiceImpl;

    /**
     * 权限操作
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取当前用户
        UserInfo userInfo = (UserInfo) principalCollection.getPrimaryPrincipal();
        // 验证是否是管理员
        if (userInfo.getId() == 11) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole("admin");
            return info;
        }
        return null;
    }


    /**
     * 认证操作
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 验证逻辑
        UserInfo userInfo = mUserServiceImpl.login(token.getUsername(), String.valueOf(token.getPassword()));
        // 将用户信息存入session
        SecurityUtils.getSubject().getSession().setAttribute("userInfo", userInfo);

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userInfo, token.getCredentials(), getName());
        return authenticationInfo;
    }
}
