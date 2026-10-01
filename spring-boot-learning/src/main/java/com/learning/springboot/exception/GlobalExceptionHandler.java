package com.learning.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 * <p>
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 * 自动捕获 Controller 抛出的异常,统一返回 JSON 格式。
 * <p>
 * 优势:
 * - 不用在每个 Controller 写 try-catch
 * - 统一异常响应格式
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", e.getCode());
        body.put("error", "Business Error");
        body.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * 处理参数校验异常(@Valid 校验失败)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 400);
        body.put("error", "Validation Failed");

        // 收集所有字段错误
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        body.put("message", "参数校验失败");
        body.put("fieldErrors", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * 处理其他未定义异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 500);
        body.put("error", "Internal Server Error");
        body.put("message", "服务器内部错误: " + e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}