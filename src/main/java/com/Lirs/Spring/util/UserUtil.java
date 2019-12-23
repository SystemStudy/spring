package com.Lirs.Spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户操作辅助类
 */
@Component
public class UserUtil {
    //最大登陆失败次数
    public static final int FAILCOUNT = 5;
    //失败次数记录过期时间
    public static final int FAILTIME = 180;
    //锁定时间
    public static final int LOCKHOURS = 2;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 查询redis中该用户是否已经被锁定，key名称为： user:username:lockTime value任意值，如果没有被锁定，则返回-1
     * @param username 用户名
     * @return 该用户是否被锁定，如果是，返回剩余锁定时间，如果否，返回-1
     */
    public long getUserLoginTimeLock(String username){
        String key = "user:" + username + ":lockTime";
        if(redisUtil.hasKey(key)){//判断redis中是否有该用户的lock
            return redisUtil.getExpireSeconds(key);
        }else{
            return -1;
        }
    }

    /**
     * 查询redis中是否有该用户的剩余登陆次数记录，如果有，则返回剩余登陆次数，如果没有，则返回-1 key：user:username:failCount value 等于登陆次数
     * @param username  用户名
     * @return  剩余登陆次数 or -1
     */
    public int getUserFailCount(String username){
        String key = "user:" + username + ":failCount";
        if(redisUtil.hasKey(key)){//判断数据中是否有该用户的登录失败次数记录
            return (Integer) redisUtil.get(key);
        }else{
            return -1;
        }
    }
}
