package com.wizard_lhx.springboot.mapper;

import com.wizard_lhx.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into `user` (username,password,name,phone,avatar)" +
            "values (#{username},#{password},#{name},#{phone},#{avatar})")
    void insert(User user);

    @Update("update `user` set username = #{username}, password = #{password}, " +
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

    @Select("select * from `user` where username like concat('%',#{username},'%')")
    List<User> selectBlur(@Param("username") String username);

    //分页查询
    @Select("select * from `user` limit #{skipNum},#{pageSize}")
    List<User> selectPage(@Param("skipNum") int skipNum, @Param("pageSize") int pageSize);

    //查询总数
    @Select("select count(id) from `user`")
    int selectCount();

    @Select("select * from `user` where username = #{username}")
    User selectByUsername(String username);
}
