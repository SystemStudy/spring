package com.Lirs.Spring.Controller;

import com.Lirs.Spring.aliMapper.AliUserMapper;
import com.Lirs.Spring.annotation.Log;
import com.Lirs.Spring.dao.UserDao;
import com.Lirs.Spring.localMapper.UserMapper;
import com.Lirs.Spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper mapper;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AliUserMapper ali_userMapper;

    @Log("本地数据库获取所有记录")
    @RequestMapping("/getAll")
    public List<User> getAll(){
        return mapper.selectAll();
    }

    @Log("本地数据库根据ID获取记录")
    @RequestMapping("/selectById")
    public User selectById(String id){
        return mapper.selectById(id);
    }

    @Log("本地数据库使用JDBC根据ID获取数据")
    @RequestMapping("/selectByIdUseJDBC")
    public User selectByIdUseJDBC(String id){
        return userDao.selectById(id);
    }

    @Log("本地数据库使用JDBC获取所有记录")
    @RequestMapping("/selectUseJDBC")
    public List<User> selectUseJDBC(){
        return userDao.selectAll();
    }

    @Log("阿里云服务器获取所有记录")
    @RequestMapping("/selectByAli")
    public List<User> selectUserAli(){
        return ali_userMapper.selectAll();
    }

    @Log("本地数据库新增")
    @RequestMapping("/add")
    public boolean add(){
        User user = new User();
        user.setId("4");
        user.setAge(18);
        user.setSex("男");
        user.setUsername("zhangsan");
        user.setPassowrd("123");
        int count = userDao.save(user);
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }

    @Log("本地数据库修改")
    @RequestMapping("/update")
    public boolean update(){
        User user = new User();
        user.setAge(18);
        user.setSex("男");
        user.setUsername("wangwu");
        user.setPassowrd("123");
        int count = userDao.update(user);
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }
    @RequestMapping("/testThrow")
    public void testThrow(){
        System.out.println("这个方法会抛出异常哦");
        try{
            throw new RuntimeException();
        }catch (RuntimeException ex){
            System.out.println("我自己捕获处理了异常：" + ex.toString());
        }
    }
}
