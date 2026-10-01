package com.learning.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 上下文加载测试
 * <p>
 * @SpringBootTest 会启动完整的 Spring Boot 上下文,
 * 用于验证应用能正常启动。
 */
@SpringBootTest
class SpringBootLearningApplicationTests {

    @Test
    void contextLoads() {
        // 如果 Spring 上下文加载失败,这个测试就会失败
    }
}