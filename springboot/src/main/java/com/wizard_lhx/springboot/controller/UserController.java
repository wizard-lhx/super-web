package com.wizard_lhx.springboot.controller;

import com.wizard_lhx.springboot.common.Result;
import com.wizard_lhx.springboot.entity.User;
import com.wizard_lhx.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 * 作者：wizard-lhx
 * 日期：2024/10/15 20:16
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    // 新增用户信息，传入JSON参数
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

    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.updateUser(user);
        return Result.success(user);
    }

    //传入路径参数
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable int id) {
        userService.deleteUser(id);
        return Result.success();
    }

    //批量删除
    @DeleteMapping("/deleteBatch")
    public Result delete(@RequestBody List<Integer> ids) {
        userService.deleteUser(ids);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<User> user = userService.selectAll();
        return Result.success(user);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable int id) {
        User user = userService.selectById(id);
        return Result.success(user);
    }

    @GetMapping("/selectByName/{name}")
    public Result selectByName(@PathVariable String name) {
        List<User> user = userService.selectByName(name);
        return Result.success(user);
    }

    //传入url参数
    @GetMapping("/selectByMore")
    public Result selectByMore(@RequestParam String name, @RequestParam int id) {
        List<User> user = userService.selectByMore(name, id);
        return Result.success(user);
    }

    @GetMapping("/selectBlur")
    public Result selectBlur(@RequestParam String user_name) {
        List<User> user = userService.selectBlur(user_name);
        return Result.success(user);
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        Map<String, Object> result = userService.selectPage(pageNum, pageSize);
        return Result.success(result);
    }
}
