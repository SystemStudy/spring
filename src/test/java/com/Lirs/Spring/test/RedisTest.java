//package com.Lirs.Spring.test;
//
//import com.Lirs.Spring.util.RedisUtil;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.HashMap;
//
//@SpringBootTest
//public class RedisTest {
//    @Autowired
//    RedisUtil redisUtil;
//
//    @Test
//    public void redis(){
//        Double result = redisUtil.incr("number",new Double(1));
//        Double result2 = redisUtil.decr("number",new Double(0.1));
//        System.out.println(result);
//        System.out.println(result2);
//    }
//
//    @Test
//    public void redisHash(){
//        boolean is = redisUtil.hset("user","username","admin");
//        if(is)
//            System.out.println(redisUtil.hmget("user"));
//    }
//
//    @Test
//    public void redisHashDelete(){
//        Object args[] = {"username","password","remark"};
//        HashMap<String,Object> map = new HashMap<>();
//        map.put("username","admin");
//        map.put("password","123");
//        map.put("remark","abc");
//        map.put("sex","man");
//        redisUtil.hmset("user:Lirs",map);
//        System.out.println(redisUtil.hmget("user:Lirs"));
//        redisUtil.hdel("user:Lirs",args);
//        System.out.println(redisUtil.hmget("user:Lirs"));
//    }
//
//    @Test
//    public void redisHexists(){
//        System.out.println(redisUtil.hexists("user:Lirs","sex"));
//        System.out.println(redisUtil.hexists("user:Lirs","username"));
//        //System.out.println(redisUtil.hexists("user:Lirs",null));  java.lang.IllegalArgumentException: non null hash key required
//        //System.out.println(redisUtil.hexists(null,"sex"));  java.lang.IllegalArgumentException: non null key required
//    }
//
//    @Test
//    public void redisHincr(){
//        System.out.println(redisUtil.hincr("user:Lirs","age",1));
//        System.out.println(redisUtil.hmget("user:Lirs"));
//        System.out.println(redisUtil.hdincr("user:Lirs","age",1));
//    }
//    @Test
//    public void redisExpire(){
////        redisUtil.set("user","1",5400);
////        System.out.println(redisUtil.getExpireSeconds("user"));
////        System.out.println(redisUtil.getExpireMinutes("user"));
////        System.out.println(redisUtil.getExpireHours("user"));
////        System.out.println(redisUtil.getExpireDays("user"));
//        System.out.println(redisUtil.hasKey("jdiwaojdioaw"));
//        System.out.println(redisUtil.getExpireSeconds("username"));
//    }
//
//}
