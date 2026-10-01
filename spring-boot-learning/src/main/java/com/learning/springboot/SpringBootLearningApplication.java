package com.learning.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 应用启动类
 * <p>
 * 核心注解:
 * - @SpringBootApplication: 组合注解,等同于
 *   @Configuration + @EnableAutoConfiguration + @ComponentScan
 * <p>
 * SpringApplication.run() 启动 Spring Boot 应用,自动配置内嵌 Tomcat 等。
 */
@SpringBootApplication
public class SpringBootLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLearningApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("✓ Spring Boot 应用启动成功!");
        System.out.println("✓ 访问 http://localhost:8080/api/users 测试 API");
        System.out.println("========================================\n");
    }
}