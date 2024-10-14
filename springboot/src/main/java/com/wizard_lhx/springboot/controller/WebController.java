package com.wizard_lhx.springboot.controller;

import com.wizard_lhx.springboot.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能：提供返回数据接口
 * 作者：wizard-lhx
 * 日期：2024/10/14 16:44
 */
@RestController
public class WebController {
    @RequestMapping
    public Result hello(String name) {
        return Result.success(name);
    }
}
