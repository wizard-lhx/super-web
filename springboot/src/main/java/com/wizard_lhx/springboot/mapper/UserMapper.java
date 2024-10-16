package com.wizard_lhx.springboot.mapper;

import com.wizard_lhx.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into `user` (user_name,password,name,phone,avatar)" +
            "values (#{user_name},#{password},#{name},#{phone},#{avatar})")
    void insert(User user);

    @Update("update `user` set user_name = #{user_name}, password = #{password}, " +
            "name = #{name}, phone = #{phone}, avatar = #{avatar} where id = #{id}")
    void update(User user);

    @Delete("delete from `user` where id = #{id}")
    void delete(int id);

    @Select("select * from `user` order by id desc")
    List<User> selectAll();

    @Select("select * from `user` where id = #{id}")
    List<User> selectById(int id);

    @Select("select * from `user` where name = #{name}")
    List<User> selectByName(String name);

    @Select("select * from `user` where name = #{name} and id = #{id}")
    List<User> selectByMore(@Param("name") String name, @Param("id")int id);

    @Select("select * from `user` where user_name like concat('%',#{user_name},'%')")
    List<User> selectBlur(@Param("user_name") String user_name);

    //分页查询
    @Select("select * from `user` limit #{skipNum},#{pageSize}")
    List<User> selectPage(@Param("skipNum") int skipNum, @Param("pageSize") int pageSize);

    //查询总数
    @Select("select count(id) from `user`")
    int selectCount();
}
