package com.mhx.finance.dao;

import com.mhx.finance.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> getUserAll();

    List<User> getUser(String family_id);

    boolean setUser(User user);

    List<User> getUserId(User user);

    void insertUser(User user);

    User findByUsername(String username);
}
