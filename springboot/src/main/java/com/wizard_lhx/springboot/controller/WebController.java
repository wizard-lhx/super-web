package com.wizard_lhx.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.wizard_lhx.springboot.common.AuthAccess;
import com.wizard_lhx.springboot.common.Result;
import com.wizard_lhx.springboot.entity.User;
import com.wizard_lhx.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：提供返回数据接口
 * 作者：wizard-lhx
 * 日期：2024/10/14 16:44
 */
@RestController
public class WebController {

    @Autowired
    UserService userService;

    @AuthAccess
    @GetMapping("/")
    public Result get() {
        return Result.success("hello");
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
            return Result.error("输入数据不合法");
        }
        User dbUser =  userService.login(user);
        return Result.success(dbUser);
    }

    @AuthAccess
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword()) || StrUtil.isBlank(user.getRole())){
            return Result.error("输入数据不合法");
        }
        User dbUser = userService.register(user);
        return Result.success(dbUser);
    }
}
