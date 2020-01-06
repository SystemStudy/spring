package com.Lirs.Spring.controller;

import com.Lirs.Spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

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
