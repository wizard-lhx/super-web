package com.wizard_lhx.springboot.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：
 * 作者：wizard-lhx
 * 日期：2024/10/14 16:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {
    public static final String CODE_SUCCESS = "200";
    public static final String CODE_AUTH_ERROR = "401";
    public static final String CODE_SYS_ERROR = "500";

    private String code;
    private String msg;
    private Object data;

    public static Result success() {
        return new Result(CODE_SUCCESS, "请求成功", null);
    }
    public static Result success(Object data) {
        return new Result(CODE_SUCCESS, "请求成功", data);
    }
    public static Result error(String msg) {
        return new Result(CODE_SYS_ERROR, msg, null);
    }
    public static Result error(String code,String msg) {
        return new Result(code, msg, null);
    }
    public static Result error() {
        return new Result(CODE_SYS_ERROR, null, null);
    }
}
