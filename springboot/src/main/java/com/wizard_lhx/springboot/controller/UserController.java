package com.wizard_lhx.springboot.controller;

import com.wizard_lhx.springboot.common.Result;
import com.wizard_lhx.springboot.entity.User;
import com.wizard_lhx.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能：
 * 作者：wizard-lhx
 * 日期：2024/10/15 20:16
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    // 新增用户信息
    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        // 异常捕获
        try{
            userService.insertUser(user);
        }catch (Exception e){
            if(e instanceof DuplicateKeyException){
                return Result.error("查询错误");
            }
            else{
                return Result.error("系统错误");
            }
        }

        return Result.success();
    }
}
