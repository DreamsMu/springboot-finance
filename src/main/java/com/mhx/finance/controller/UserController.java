package com.mhx.finance.controller;

import com.mhx.finance.domain.Capital;
import com.mhx.finance.domain.User;
import com.mhx.finance.service.CapitalService;
import com.mhx.finance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public CapitalService capitalService;

    @PostMapping("getUser")
    public List<User> getUser(User user){
        return userService.getUser(user);
    }

    @PostMapping("setUser")
    public Integer setUser(User user) {
        return userService.setUser(user);
    }

    @PostMapping("getUserId")
    public List<User> getUserId(User user) {
        return userService.getUserId(user);
    }

    @PostMapping("setUserSet")
    public Object setUserSetData(User user, Capital capital) {
        Map<Object, Object> map = new HashMap<>();
        try {
            Integer setUser = userService.setUser(user);
            if (setUser == 222 || setUser == 201) {
                map.put("code", setUser);
            };
            capitalService.setCapital(capital);
            User userId = userService.getUserId(user).get(0);
            map.put("user", userId);
            map.put("code", 200);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 201);
            return map;
        }
    }

    @PostMapping("login")
    public Object login(User user) {
        return userService.login(user);
    }

    @GetMapping("logout")
    public Object logout(User user) {
        return userService.logout();
    }

    @PostMapping("register")
    public Map<String, Object> register(User user) {
        return userService.insertUser(user);
    }
}
