package com.Lirs.Spring.controller;

import com.Lirs.Spring.model.User;
import com.Lirs.Spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/testUser")
public class TestUserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getAll")
    public List<User> getAll(){
        return userService.selectAll();
    }

    @RequestMapping("/getById")
    public User getById(String id){
        return userService.selectById(id);
    }

}
