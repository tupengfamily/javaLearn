package com.learning.springboot.exception;

/**
 * 业务异常
 * <p>
 * 与 java-learning 项目的 CustomException 类似,但作为统一异常基类。
 */
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}