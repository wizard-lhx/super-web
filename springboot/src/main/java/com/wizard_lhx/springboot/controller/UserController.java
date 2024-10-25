package com.wizard_lhx.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wizard_lhx.springboot.common.InterceptorConfig;
import com.wizard_lhx.springboot.common.Result;
import com.wizard_lhx.springboot.entity.User;
import com.wizard_lhx.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
            userService.save(user);
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
        userService.updateById(user);
        return Result.success(user);
    }

    //传入路径参数
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable int id) {
        userService.removeById(id);
        return Result.success();
    }

    //批量删除
    @DeleteMapping("/deleteBatch")
    public Result delete(@RequestBody List<Integer> ids) {
        userService.removeBatchByIds(ids);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<User> user = userService.list(new QueryWrapper<User>().orderByDesc("id"));
        return Result.success(user);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable int id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam int pageNum,
                             @RequestParam int pageSize,
                             @RequestParam String username,
                             @RequestParam String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().orderByDesc("id");
        queryWrapper.like(StrUtil.isNotBlank(username),"username",username);
        queryWrapper.like(StrUtil.isNotBlank(name),"name",name);
        Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }

    @GetMapping("/exportData")
    public void exportData(@RequestParam String username,
                           @RequestParam String name,
                           @RequestParam(required = false) String idStr,
                           HttpServletResponse response) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter(true);
        List<User> list;

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(idStr)){
            List<Integer> ids = Arrays.stream(idStr.split(",")).map(Integer::valueOf).collect(Collectors.toList());
            queryWrapper = queryWrapper.in("id", ids);
        }else {
            queryWrapper.like(StrUtil.isNotBlank(username),"username",username);
            queryWrapper.like(StrUtil.isNotBlank(name),"name",name);
        }
        list = userService.list(queryWrapper);
        writer.write(list,true);

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=user.xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream);
        writer.close();
        outputStream.flush();
        outputStream.close();

    }

    @PostMapping("/importData")
    public Result importData(@RequestParam MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<User> list = reader.readAll(User.class);

        try{
            userService.saveBatch(list);
        }catch (Exception e){
            if(e instanceof DuplicateKeyException){
                return Result.error("数据重复错误");
            }
            else{
                return Result.error("系统错误");
            }
        }
        return Result.success();
    }
}
