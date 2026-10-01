package com.learning.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello 控制器
 * <p>
 * 演示 Spring MVC 的基本用法:
 * - @Value: 读取配置文件中的值
 * - @PathVariable: 从 URL 路径取值
 * - 返回 Map 自动转为 JSON
 */
@RestController
@RequestMapping("/api")
public class HelloController {

    /**
     * 从 application.yml 中读取配置
     */
    @Value("${app.name:Spring Boot}")
    private String appName;

    @Value("${app.version:1.0.0}")
    private String version;

    /**
     * GET /api/hello - 简单的 hello
     */
    @GetMapping("/hello")
    public Map<String, Object> hello() {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Hello, " + appName + "!");
        result.put("version", version);
        result.put("timestamp", LocalDateTime.now());
        return result;
    }

    /**
     * GET /api/hello/{name} - 路径参数
     */
    @GetMapping("/hello/{name}")
    public Map<String, Object> helloName(@PathVariable String name) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Hello, " + name + "!");
        result.put("timestamp", LocalDateTime.now());
        return result;
    }
}