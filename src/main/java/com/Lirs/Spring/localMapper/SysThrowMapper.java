package com.Lirs.Spring.localMapper;

import com.Lirs.Spring.model.SysThrow;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface SysThrowMapper {
    @Insert("insert into sys_throw values(#{id},#{exception},#{time},#{method},#{params})")
    void saveThrow(SysThrow sysThrow);
}
