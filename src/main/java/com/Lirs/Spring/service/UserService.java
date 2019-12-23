package com.Lirs.Spring.service;

import com.Lirs.Spring.model.User;

import java.util.Map;

public interface UserService {

    /**
     * 用户登陆
     * @param username  账号
     * @param password  密码
     * @return  登陆成功返回User对象，失败返回null
     */
    Map<String,Object> login(String username, String password);
}
