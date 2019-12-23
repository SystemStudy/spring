package com.Lirs.Spring.controller;

import com.Lirs.Spring.localMapper.UserMapper;
import com.Lirs.Spring.model.User;
import com.Lirs.Spring.service.UserService;
import com.Lirs.Spring.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper mapper;

    @RequestMapping("/login")
    public Object login(String username, String password){
        /**
         * 验证码校验等操作
         */
        Map<String,Object> map = userService.login(username,password);
        if(map.get("success") != null){//登陆成功
            return map.get("success");
        }else{
            return map.get("message");
        }
    }


}
