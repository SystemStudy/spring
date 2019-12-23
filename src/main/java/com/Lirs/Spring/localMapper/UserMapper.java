package com.Lirs.Spring.localMapper;

import com.Lirs.Spring.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    @Insert("insert into tb_user (id,username,password,sex,age) values(#{id},#{username},#{password},#{sex},#{ages})")
    int save(User user);

    @Update("update tb_user set username = #{username},password=#{password},#sex={sex},#{age} where id = #{id}")
    int update(User user);

    @Delete("delete from tb_user where id = #{id}")
    int deleteById(String id);

    @SelectProvider(type = UserMapperProvider.class,method = "selectById")
    User selectById(String id);

    @Select("select * from tb_user")
    List<User> selectAll();

    @Select("select * from tb_user where username = #{username}")
    User selectByUserName(String username);
}
