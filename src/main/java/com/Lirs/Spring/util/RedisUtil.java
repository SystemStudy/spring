package com.Lirs.Spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired()
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 设置超时时间，单位是小时
     * @param key
     * @param time
     * @return
     */
    public  boolean expire(String key,long time){
        try{
            if(time > 0){
                redisTemplate.expire(key,time, TimeUnit.HOURS);
                return true;
            }else{
                return false;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 获取key的过期时间
     * @param key
     * @return
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除一个或多个key
     * @param key
     */
    public void del(String ... key)throws Exception{
        if(key.length < 1){
            throw new Exception("请输入参数");
        }else if(key.length > 1){
            redisTemplate.delete(CollectionUtils.arrayToList(key));
        }else{
            redisTemplate.delete(key[0]);
        }
    }

    /**
     * 获取key的value
     * @param key
     * @return
     */
    public Object get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 存入key-value
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,Object value){
        System.out.println(redisTemplate);
        try{
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }





}
