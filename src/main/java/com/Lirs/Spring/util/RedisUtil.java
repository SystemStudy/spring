package com.Lirs.Spring.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    private static JedisPoolConfig jedisPoolConfig;
    private static JedisPool jedisPool;

    static{
        jedisPoolConfig = new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(8);
        //最大空闲连接
        jedisPoolConfig.setMaxIdle(5);

        jedisPool = new JedisPool(jedisPoolConfig,"192.168.80.130",6379);
    }
    //获取连接
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
    //关闭连接
    public static void close(Jedis jedis){
        jedis.close();
    }


}
