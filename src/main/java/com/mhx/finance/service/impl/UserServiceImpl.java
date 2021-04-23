package com.mhx.finance.service.impl;

import com.mhx.finance.dao.CapitalMapper;
import com.mhx.finance.dao.UserMapper;
import com.mhx.finance.domain.Capital;
import com.mhx.finance.domain.User;
import com.mhx.finance.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public CapitalMapper capitalMapper;

    @Override
    public List<User> getUser(User user) {
        List<User> list = userMapper.getUser(user.getFamily_id());
        for(int i = 0; i < list.toArray().length; i++) {
            if (list.get(i).getId() == user.getId()) {
                list.remove(i);
            }
        }
        return list;
    }

    @Override
    @Transactional
    public Integer setUser(User user) {
        List<User> userAll = userMapper.getUserAll();
        for(User tempUser : userAll) {
            if (tempUser.getId() == user.getId()) continue;
            if (tempUser.getUsername().equals(user.getUsername())) return 222;
        }
        boolean isUser = userMapper.setUser(user);
        if (isUser) return 200;
        return 201;
    }

    @Override
    public List<User> getUserId(User user) {
        return userMapper.getUserId(user);
    }

    @Override
    public Map<String, Object> insertUser(User user) {
        Map<String, Object> map = new HashMap<>();
        List<User> userAll = userMapper.getUserAll();
        for (User item: userAll) {
            if (user.getUsername().equals(item.getUsername())) {
                map.put("message1", "用户名已存在");
                return map;
            }
        }
        List<User> user1 = getUser(user);
        Integer length = user1.toArray().length;
        Integer master = length == 0 ? 1 : 0;
        String power = length == 0 ? "1" : "0";
        user.setMaster(master);
        user.setPower(power);
        try {
            userMapper.insertUser(user);
            List<Capital> capital = capitalMapper.getCapital(user.getFamily_id());
            Integer list = capital.toArray().length;
            if (list == 0) {
                capitalMapper.insertCaptial(user.getFamily_id());
            }
            map.put("code1", 100);
            map.put("message1", "注册成功");
        } catch (Exception e) {
            map.put("message1", "注册失败");
            return map;
        }
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
            try {
                subject.login(token);
                map.put("token", true);
                map.put("code", 200);
                map.put("message", "欢迎"+token.getUsername()+"用户");
                map.put("user", subject.getSession().getAttribute("user"));
            } catch (UnknownAccountException e) {
                map.put("message", "用户不存在");
            } catch (IncorrectCredentialsException e) {
                map.put("message", "密码不正确");
            } catch (LockedAccountException e) {
                map.put("message", "账户已锁定");
            } catch (AuthenticationException e) {
                map.put("message", "登录失败");
            }
        } else {
            map.put("message", "该用户已登录");
        }
        return map;
    }

    @Override
    public Object login(User user) {
        Map<String, Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
//        if (!subject.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
            try {
                subject.login(token);
                map.put("token", true);
                map.put("code", 200);
                map.put("message", "欢迎"+token.getUsername()+"用户");
                map.put("user", subject.getSession().getAttribute("user"));
            } catch (UnknownAccountException e) {
                map.put("message", "用户不存在");
            } catch (IncorrectCredentialsException e) {
                map.put("message", "密码不正确");
            } catch (LockedAccountException e) {
                map.put("message", "账户已锁定");
            } catch (AuthenticationException e) {
                map.put("message", "登录失败");
            }
//        } else {
//            map.put("message", "该用户已登录");
//        }
        return map;
    }

    public Object logout() {
        Map<String, Object> map = new HashMap<>();
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            map.put("message", "登出成功");
            map.put("code", "200");
        } catch (Exception e) {
            map.put("message", "失败");
            map.put("code", "201");
        }
        return map;
    }
}
