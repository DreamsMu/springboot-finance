package com.mhx.finance.shiro;

import com.mhx.finance.dao.UserMapper;
import com.mhx.finance.domain.User;
import com.mhx.finance.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class Realm extends AuthorizingRealm {

    @Autowired
    public UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userMapper.findByUsername(token.getUsername());
        if (user == null) return null;
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("user", user);
        return new SimpleAuthenticationInfo(user,user.getPassword(),"realm");
    }
}
