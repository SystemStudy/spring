package com.Lirs.Spring.test;

import com.Lirs.Spring.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {

    @Autowired
    RedisUtil util;

    @Test
    public void redis(){
        util.set("Lirs","Xiaozhen");
        System.out.println(util.get("Lirs"));
    }

}
