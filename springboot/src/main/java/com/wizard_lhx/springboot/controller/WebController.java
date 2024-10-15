package com.wizard_lhx.springboot.controller;

import com.wizard_lhx.springboot.common.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：提供返回数据接口
 * 作者：wizard-lhx
 * 日期：2024/10/14 16:44
 */
@RestController
public class WebController {
    @GetMapping("/get")
    public Result get(@RequestBody Obj obj) {
        return Result.success(obj);
    }

    @PostMapping("/post")
    public Result post(@RequestBody Obj obj) {
        return Result.success(obj);
    }

    @PutMapping("/put")
    public Result put(@RequestBody Obj obj) {
        return Result.success(obj);
    }
    @DeleteMapping("/delete")
    public Result delete(@RequestBody Obj obj) {
        return Result.success(obj);
    }
}
