package com.Lirs.Spring.dao;

import com.Lirs.Spring.model.User;

import java.util.List;

public interface UserDao {

    int save(User user);
    int update(User user);
    int deleteById(String id);
    User selectById(String id);
    List<User> selectAll();
}
