package com.Lirs.Spring.test;

import com.Lirs.Spring.Application;
import com.Lirs.Spring.localMapper.UserMapper;
import com.Lirs.Spring.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class CacheTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test()throws Exception{
        User user = userMapper.selectById("1");
        System.out.println("查询第一条数据" + user);
        User user2 = userMapper.selectById("1");
        System.out.println("查询第二条数据：" + user2);
    }
}
