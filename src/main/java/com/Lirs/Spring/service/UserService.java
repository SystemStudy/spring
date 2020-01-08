package com.Lirs.Spring.service;

import com.Lirs.Spring.model.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@CacheConfig(cacheNames = "user")
public interface UserService {

    /**
     * 用户登陆
     * @param username  账号
     * @param password  密码
     * @return  登陆成功返回User对象，失败返回null
     */
    Map<String,Object> login(String username, String password);

    /**
     * 查询所有用户记录
     * @return
     */
    @Cacheable(key = "alluser")
    List<User> selectAll();

    /**
     * 根据用户id删除记录
     * @param id
     */
    @CacheEvict(key = "#a0",allEntries = true)
    void deleteUserById(String id);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @CachePut(key = "#a0.id")
    User update(User user);

    @Cacheable(key = "#a0")
    User selectById(String id);


}
