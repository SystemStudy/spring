package com.Lirs.Spring.localMapper;
import org.apache.ibatis.jdbc.SQL;

/**
 * 这个类是为了实现动态SQL供mapper调用的Provider
 */
public class UserMapperProvider {

    public String selectById(String id){
        return new SQL(){{
            SELECT("*");
            FROM("tb_user");
            if(id != null){
                WHERE("id = #{id}");
            }
        }}.toString();
    }
}
