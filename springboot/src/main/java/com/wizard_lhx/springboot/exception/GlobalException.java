package com.wizard_lhx.springboot.exception;

import com.wizard_lhx.springboot.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能：
 * 作者：wizard-lhx
 * 日期：2024/10/17 19:13
 */
@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result serviceException(Exception e) {
        return Result.error("500", e.getMessage());
    }
}
