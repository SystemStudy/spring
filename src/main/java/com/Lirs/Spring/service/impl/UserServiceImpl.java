package com.Lirs.Spring.service.impl;

import com.Lirs.Spring.localMapper.UserMapper;
import com.Lirs.Spring.model.User;
import com.Lirs.Spring.service.UserService;
import com.Lirs.Spring.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final int FAILCOUNT = 5;
    private final int LOCKHOURS = 2;
    private final int FAILTIME = 180;


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户登陆
     * @param username  账号
     * @param password  密码
     * @return
     */
    @Override
    public Map<String,Object> login(String username, String password){
        HashMap<String,Object> map = new HashMap<>();
        long lockTime = this.getUserLoginTimeLock(username);
        String key = "user:" + username + ":failCount";
        if(lockTime > 0){//判断用户是否已经被锁定
            map.put("message","该账号已经被锁定请在" + lockTime + "秒之后尝试");
            return map;
        }
        User user = userMapper.selectByUserName(username);
        if(user != null && password.equals(user.getPassword())){//检验用户输入的账号密码是否正确
            map.put("success",user);
            redisUtil.del(key);
            return map;
        }else{
            //设置用户登陆失败次数
            this.setFailCount(username);
            int count =(Integer)redisUtil.get(key);
            if(count == FAILCOUNT){//判断是否已经达到了最大失败次数
                String lockkey = "user:" + username + ":lockTime";
                redisUtil.set(lockkey,"1",LOCKHOURS * 60 * 60);//设置锁定时间为2小时
                redisUtil.del(key);
                map.put("message","当前账号已经被锁定，请在 " + LOCKHOURS + "小时之后再尝试");
                return map;
            }
            //没有达到5次，返回剩余登陆次数
            count = FAILCOUNT - count;
            map.put("message","登陆失败，您还剩" + count +"次登陆机会");
            return map;
        }
    }


    /**
     * 检查用户是否已经被锁定，如果是，返回剩余锁定时间，如果否，返回-1
     * @param username  username
     * @return  时间
     */
    private int getUserLoginTimeLock(String username) {
        String key = "user:" + username + ":lockTime";
        int lockTime = (int)redisUtil.getExpireSeconds(key);
        if(lockTime > 0){//查询用户是否已经被锁定，如果是，返回剩余锁定时间，如果否，返回-1
            return lockTime;
        }else{
            return -1;
        }
    }

    /**
     * 设置失败次数
     * @param username  username
     */
    private void setFailCount(String username){
        long count = this.getUserFailCount(username);
        String key = "user:" + username + ":failCount";
        if(count < 0){//判断redis中是否有该用户的失败登陆次数，如果没有，设置为1，过期时间为2分钟，如果有，则次数+1
            redisUtil.set(key,1,FAILTIME);
        }else{
            redisUtil.incr(key,new Double(1));
        }
    }

    /**
     * 获取当前用户已失败次数
     * @param username  username
     * @return  已失败次数
     */
    private int getUserFailCount(String username){
        String key = "user:" + username + ":failCount";
        //从redis中获取当前用户已失败次数
        Object object = redisUtil.get(key);
        if(object != null){
            return (int)object;
        }else{
            return -1;
        }
    }

}
