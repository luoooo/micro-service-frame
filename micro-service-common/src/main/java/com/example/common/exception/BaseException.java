package com.example.common.exception;

import com.example.common.response.ResultCode;
import lombok.Getter;

/**
 * 基础异常类
 */
@Getter
public class BaseException extends RuntimeException {
    private final String code;
    private final String message;

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = String.valueOf(resultCode.getCode());
        this.message = resultCode.getMessage();
    }

    public BaseException(ResultCode resultCode, String message) {
        super(message);
        this.code = String.valueOf(resultCode.getCode());
        this.message = message;
    }
} 