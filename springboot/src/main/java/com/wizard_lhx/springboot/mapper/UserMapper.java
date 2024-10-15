package com.wizard_lhx.springboot.mapper;

import com.wizard_lhx.springboot.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into `user` (user_name,password,name,phone,avatar)" +
            "values (#{user_name},#{password},#{name},#{phone},#{avatar})")
    public void insert(User user);
}
