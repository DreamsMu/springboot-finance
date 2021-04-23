package com.mhx.finance.service;

import com.mhx.finance.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getUser(User user);

    Integer setUser(User user);

    List<User> getUserId(User user);

    Map<String, Object> insertUser(User user);

    Object login(User user);

    Object logout();
}
