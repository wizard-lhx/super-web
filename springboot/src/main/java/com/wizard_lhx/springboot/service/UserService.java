package com.wizard_lhx.springboot.service;

import com.wizard_lhx.springboot.entity.User;
import com.wizard_lhx.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能：
 * 作者：wizard-lhx
 * 日期：2024/10/15 20:16
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void insertUser(User user) {
        userMapper.insert(user);
    }
}
