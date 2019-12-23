package com.Lirs.Spring.dao.impl;

import com.Lirs.Spring.dao.UserDao;
import com.Lirs.Spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final int[] TYPE = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};

    @Override
    public int save(User user) {
       String sql = "insert into tb_user (id,username,password,sex,age) values(?,?,?,?,?)";
        Object agrs[] = {user.getId(),user.getUsername(),user.getPassword(),user.getSex(),user.getAge()};
        return jdbcTemplate.update(sql,agrs,TYPE);

    }

    @Override
    public int update(User user) {
        String sql = "update tb_user set username = ? ,password = ? ,sex = ?, age = ? where id = '1'";
        Object args[] = {user.getUsername(),user.getPassword(),user.getSex(),user.getAge()};
        int argsType[] = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
        return jdbcTemplate.update(sql,args,argsType);
    }

    @Override
    public int deleteById(String id) {
        String sql = "delete from tb_user where id = ?";
        int argsType[] = {Types.VARCHAR};
        Object args[] = {id};
        return jdbcTemplate.update(sql,args,argsType);
    }

    @Override
    public User selectById(String id) {
        String sql = "select * from tb_user where id = ?";
        Object args[] = {id};
        int argsTypes[] = {Types.VARCHAR};
        List<User> list = jdbcTemplate.query(sql,args,new UserMapper());
        if(list != null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }


    @Override
    public List selectAll() {
        String sql = "select * from tb_user";
        return jdbcTemplate.queryForList(sql);

        //List<User> users = jdbcTemplate.query(sql,new UserMapper());
        //return users;
        /**
         *还可以使用这种写法。
         */
        //return jdbcTemplate.queryForList(sql);
    }
}

class UserMapper implements RowMapper<User>{

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setPassword(resultSet.getString("password"));
        user.setUsername(resultSet.getString("username"));
        user.setSex(resultSet.getString("sex"));
        user.setAge(resultSet.getInt("age"));
        return user;
    }
}