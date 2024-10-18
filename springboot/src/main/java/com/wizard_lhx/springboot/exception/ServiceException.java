package com.wizard_lhx.springboot.exception;

import lombok.Getter;

/**
 * 功能：
 * 作者：wizard-lhx
 * 日期：2024/10/17 19:16
 */
@Getter
public class ServiceException extends RuntimeException {

    private final String code;

    public ServiceException(String message) {
        super(message);
        this.code = "500";
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }
}
