package com.wizard_lhx.springboot.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wizard_lhx.springboot.common.Result;
import com.wizard_lhx.springboot.entity.User;
import com.wizard_lhx.springboot.exception.ServiceException;
import com.wizard_lhx.springboot.mapper.UserMapper;
import com.wizard_lhx.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 * 作者：wizard-lhx
 * 日期：2024/10/15 20:16
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Resource
    UserMapper userMapper;

    @Override
    public boolean save(User user) {
        if(StrUtil.isBlank(user.getName())){
            user.setName(user.getUsername());
        }
        if(StrUtil.isBlank(user.getPassword())){
            user.setPassword("123");
        }
        if(StrUtil.isBlank(user.getRole())){
            user.setRole("student");
        }
        return super.save(user);
    }

    public User selectByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return getOne(queryWrapper);
    }

    public User login(User user) {
        User dbUser = selectByUsername(user.getUsername());
        if (dbUser == null) {
            throw new ServiceException("账号或密码不存在");
        }
        if(!user.getPassword().equals(dbUser.getPassword())) {
            throw new ServiceException("账号或密码不存在");
        }
        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);

        return dbUser;
    }

    public User register(User user) {
        User dbUser = selectByUsername(user.getUsername());
        if (dbUser != null) {
            throw new ServiceException("账号已存在");
        }
        userMapper.insert(user);
        return user;
    }
}
