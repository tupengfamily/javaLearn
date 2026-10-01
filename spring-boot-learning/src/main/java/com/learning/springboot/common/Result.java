package com.learning.springboot.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 统一响应工厂
 * <p>
 * 所有 Controller 通过 Result.ok(...) / Result.error(...) / Result.unauthorized() 返回,
 * 保证响应格式一致: { code, message, data, timestamp }
 */
public final class Result {

    public static final int CODE_SUCCESS = 200;
    public static final int CODE_BAD_REQUEST = 400;
    public static final int CODE_UNAUTHORIZED = 401;
    public static final int CODE_FORBIDDEN = 403;
    public static final int CODE_NOT_FOUND = 404;
    public static final int CODE_CONFLICT = 409;
    public static final int CODE_ERROR = 500;

    private Result() {}

    public static ResponseEntity<Map<String, Object>> ok(Object data) {
        return build(HttpStatus.OK, CODE_SUCCESS, "操作成功", data);
    }

    public static ResponseEntity<Map<String, Object>> ok(String message, Object data) {
        return build(HttpStatus.OK, CODE_SUCCESS, message, data);
    }

    public static ResponseEntity<Map<String, Object>> created(String message, Object data) {
        return build(HttpStatus.CREATED, CODE_SUCCESS, message, data);
    }

    public static ResponseEntity<Map<String, Object>> error(HttpStatusCode status, int code, String message) {
        return build(status, code, message, null);
    }

    public static ResponseEntity<Map<String, Object>> badRequest(String message) {
        return build(HttpStatus.BAD_REQUEST, CODE_BAD_REQUEST, message, null);
    }

    public static ResponseEntity<Map<String, Object>> unauthorized(String message) {
        return build(HttpStatus.UNAUTHORIZED, CODE_UNAUTHORIZED, message, null);
    }

    public static ResponseEntity<Map<String, Object>> forbidden(String message) {
        return build(HttpStatus.FORBIDDEN, CODE_FORBIDDEN, message, null);
    }

    public static ResponseEntity<Map<String, Object>> conflict(String message) {
        return build(HttpStatus.CONFLICT, CODE_CONFLICT, message, null);
    }

    private static ResponseEntity<Map<String, Object>> build(HttpStatusCode status, int code, String message, Object data) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", code);
        body.put("message", message);
        body.put("data", data);
        body.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(status).body(body);
    }
}