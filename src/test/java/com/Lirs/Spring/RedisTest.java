package com.Lirs.Spring;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisTest {

    @Test
    public void test() throws Exception{
        //获取redis连接
        Jedis jedis = new Jedis("192.168.80.130",6379);
        //判断redis中是否有该数据，如果没有，则去mysql这些关系型数据库中取，并将其放入到redis中，方便下次再用
        if(jedis.exists("key")){
            System.out.println(jedis.get("key"));
        }else{
            Thread.sleep(1000);
            jedis.set("key","value");
        }
        jedis.close();
    }

    @Test
    public void redisPool() throws Exception{
        //连接池的配置信息
        JedisPoolConfig config = new JedisPoolConfig();
        //最大连接数
        config.setMaxTotal(5);
        //最大空闲连接
        config.setMaxIdle(3);
        //创建redis连接池
        JedisPool pool = new JedisPool(config,"192.168.80.130",6379);
        //从连接池中获取redis连接
        Jedis jedis = pool.getResource();
        jedis.auth("960215@Xiaozhen");
    }
}
