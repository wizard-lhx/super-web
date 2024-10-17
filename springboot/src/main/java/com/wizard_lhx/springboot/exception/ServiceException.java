package com.wizard_lhx.springboot.exception;

/**
 * 功能：
 * 作者：wizard-lhx
 * 日期：2024/10/17 19:16
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
