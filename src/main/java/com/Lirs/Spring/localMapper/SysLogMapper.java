package com.Lirs.Spring.localMapper;

import com.Lirs.Spring.model.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

@Repository
public interface SysLogMapper {
    @Insert("insert into sys_log(id,username,operation,time,method,ip,create_time,params) " +
            "values (#{id},#{username},#{operation},#{time},#{method},#{ip},#{create_time},#{params})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int saveLog(SysLog log);

}
