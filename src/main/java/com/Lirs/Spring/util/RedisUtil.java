package com.Lirs.Spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
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
    //************************String*********************************
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
        try{
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 存放key-value的同时设置过期时间。
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key,Object value,long time){
        try{
            if(time > 0){
                redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            }else{
                redisTemplate.opsForValue().set(key,value);
            }
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 指定key进行自增number
     * @param key  redis中的kye
     * @param number number必须为Double 推荐new Double();
     * @return  返回自增后的结果，类型为Double
     */
    public Double incr(String key, Double number){
        if(number < 0){
            new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key,number);
    }

    /**
     * 指定key进行自减number
     * @param key  redis中的kye
     * @param number number必须为Double 推荐new Double();
     * @return  返回自减后的结果，类型为Double
     */
    public Double decr(String key,Double number){
        if(number < 0){
            new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key,-number);
    }

    //*************************Hash*****************************

    public boolean hset(String key,String field,Object value){
        try{
            redisTemplate.opsForHash().put(key,field,value);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 指定key 中 指定的field对应的value
     * @param key key
     * @param field field
     * @return key中field对应的value
     */
    public Object hget(String key,String field){
        return redisTemplate.opsForHash().get(key,field);
    }

    /**
     * 返回对应key的所有键值对
     * @param key   key
     * @return  所有键值对
     */
    public Map<Object,Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

}
