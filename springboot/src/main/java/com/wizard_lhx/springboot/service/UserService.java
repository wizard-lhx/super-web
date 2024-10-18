package com.wizard_lhx.springboot.service;

import com.wizard_lhx.springboot.common.Result;
import com.wizard_lhx.springboot.entity.User;
import com.wizard_lhx.springboot.exception.ServiceException;
import com.wizard_lhx.springboot.mapper.UserMapper;
import com.wizard_lhx.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void updateUser(User user) {
        userMapper.update(user);
    }

    public void deleteUser(int id) {
        userMapper.delete(id);
    }
    public void deleteUser(List<Integer> ids) {
        for (Integer id : ids) {
            userMapper.delete(id);
        }
    }

    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    public User selectById(int id) {
        return userMapper.selectById(id);
    }

    public List<User> selectByName(String name) {
        return userMapper.selectByName(name);
    }

    public List<User> selectByMore(String name, int id) {
        return userMapper.selectByMore(name,id);
    }

    public List<User> selectBlur(String user_name) {
        return userMapper.selectBlur(user_name);
    }

    public Map<String, Object> selectPage(int pageNum, int pageSize) {
        int skipNum = (pageNum - 1) * pageSize;
        Map<String, Object> result = new HashMap<>();
        List<User> user = userMapper.selectPage(skipNum,pageSize);
        int total = userMapper.selectCount();
        result.put("total", total);
        result.put("user", user);
        return result;
    }

    public User login(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
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
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser != null) {
            throw new ServiceException("账号已存在");
        }
        userMapper.insert(user);
        return user;
    }
}
