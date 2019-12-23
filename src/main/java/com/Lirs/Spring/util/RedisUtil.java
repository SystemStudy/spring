package com.Lirs.Spring.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisUtil {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    /**
     * 设置超时时间，单位是秒
     * @param key
     * @param time
     * @return
     */
    public  boolean expire(String key,long time){
        try{
            if(time > 0){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
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
     * 获取key的过期时间，单位：秒
     * @param key   key
     * @return  过期时间
     */
    public long getExpireSeconds(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 获取key的过期时间，单位：分钟
     * @param key   key
     * @return  过期时间
     */
    public long getExpireMinutes(String key){
        return redisTemplate.getExpire(key,TimeUnit.MINUTES);
    }

    /**
     * 获取key的过期时间，单位：小时
     * @param key   key
     * @return  过期时间
     */
    public long getExpireHours(String key){
        return redisTemplate.getExpire(key,TimeUnit.HOURS);
    }

    /**
     * 获取key的过期时间，单位：天
     * @param key   key
     * @return  过期时间
     */
    public long getExpireDays(String key){
        return redisTemplate.getExpire(key,TimeUnit.DAYS);
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        Boolean result = redisTemplate.hasKey(key);
        if(result != null)
            return result;
        return false;
    }

    /**
     * 删除一个或多个key
     * @param key
     */
    public void del(String ... key){
        if(key.length < 1){
           new Exception("请输入参数").printStackTrace();
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
     * @param number 如果需要传入整数，可以使用new Double();
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

    /**
     * 为key设置field-value
     * @param key   key
     * @param field field
     * @param value value
     * @return  是否成功
     */
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
     * 为key设置field-value，过期时间time
     * @param key key
     * @param field filed
     * @param value value
     * @param time 过期时间time
     * @return 是否成功
     */
    public boolean hset(String key,String field,Object value,long time){
        try{
            redisTemplate.opsForHash().put(key,field,value);
            if(time > 0){
                expire(key,time);
            }
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

    /**
     * 为key设置多个field-value
     * @param key   key
     * @param map   field-value
     * @return  是否存储成功
     */
    public boolean hmset(String key,Map<String,Object> map){
        try{
            redisTemplate.opsForHash().putAll(key,map);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 存储多个field-value，并设置key的过期时间
     * @param key   key
     * @param map   field-value
     * @param time  过期时间，秒为单位
     * @return 是否成功
     */
    public boolean hmset(String key,Map<String,Object> map,long time){
        try{
            redisTemplate.opsForHash().putAll(key,map);
            if(time > 0){
                expire(key,time);
            }
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 删除指定key下的一对或多对fields-value
     * @param key   key
     * @param fields    field
     * @return  是否执行成功
     */
    public boolean hdel(String key,Object ... fields){//可变参数其实类型可以是String，但是编译器会给出警告，所以最好还是使用Object，传入的参数也使用Object[]
        if(fields != null && fields.length > 0){
            redisTemplate.opsForHash().delete(key,fields);
        }else{
            new RuntimeException("field不能为null").printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断指定key中是否存在field
     * @param key   key
     * @param field field
     * @return  存在返回true，否则返回false
     */
    public boolean hexists(String key,Object field){
        if(key != null && field != null){
            return redisTemplate.opsForHash().hasKey(key,field);
        }else{
            new IllegalArgumentException("key和field都不能为空").printStackTrace();
            return false;
        }
    }

    /**
     * 对hash表中指定key的field自增num，如果field不存在，则会初始化0
     * @param key   key
     * @param field field
     * @param num   num
     * @return  自增后的结果
     */
    public Double hincr(String key,Object field,double num){
        if(num > 0){
            return redisTemplate.opsForHash().increment(key,field,num);
        }else{
            new IllegalArgumentException("num不能小于0").printStackTrace();
            return null;
        }
    }

    /**
     * 对hash表中指定key的field自减num，如果field不存在，则会初始化0
     * @param key   key
     * @param field field
     * @param num   num
     * @return  自减后的结果
     */
    public Double hdincr(String key,Object field,double num){
        if(num > 0){
            return redisTemplate.opsForHash().increment(key,field,-num);
        }else{
            new IllegalArgumentException("num不能小于0").printStackTrace();
            return null;
        }
    }






}
