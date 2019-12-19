package com.Lirs.Spring.test;

import com.Lirs.Spring.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {
    @Autowired
    RedisUtil redisUtil;



    @Test
    public void redis(){
        Double result = redisUtil.incr("number",new Double(1));
        Double result2 = redisUtil.decr("number",new Double(0.1));
        System.out.println(result);
        System.out.println(result2);
    }

    @Test
    public void redisHash(){
        boolean is = redisUtil.hset("user","username","admin");
        if(is)
            System.out.println(redisUtil.hmget("user"));
    }

}
