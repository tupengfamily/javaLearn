package com.learning.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 单元测试
 * <p>
 * JUnit 是 Java 最流行的测试框架。
 * <p>
 * 常用注解:
 * - @Test: 标记测试方法
 * - @DisplayName: 自定义显示名称
 * - @BeforeEach / @AfterEach: 每个测试前后执行
 * - @BeforeAll / @AfterAll: 所有测试前后执行(静态方法)
 * <p>
 * 常用断言:
 * - assertEquals(expected, actual)
 * - assertTrue(condition)
 * - assertFalse(condition)
 * - assertNull(obj)
 * - assertNotNull(obj)
 * - assertThrows(ExceptionClass, executable)
 */
@DisplayName("数据类型测试")
class DataTypesTest {

    @Test
    @DisplayName("基本类型转换")
    void testPrimitiveConversion() {
        int intVal = 100;
        long longVal = intVal; // 自动转换
        double doubleVal = longVal;

        assertEquals(100L, longVal);
        assertEquals(100.0, doubleVal, 0.001);
    }

    @Test
    @DisplayName("强制类型转换会丢失精度")
    void testForceCastLosesPrecision() {
        double pi = 3.14159;
        int intPi = (int) pi;
        assertEquals(3, intPi); // 0.14159 丢失
    }

    @Test
    @DisplayName("自动装箱拆箱")
    void testAutoBoxing() {
        Integer intObj = 100;  // 装箱
        int intVal = intObj;   // 拆箱
        assertEquals(100, intVal);
    }

    @Test
    @DisplayName("字符串常用方法")
    void testStringMethods() {
        String text = "Hello, Java";
        assertEquals(11, text.length());
        assertTrue(text.contains("Java"));
        assertTrue(text.startsWith("Hello"));
        assertEquals("HELLO, JAVA", text.toUpperCase());
    }

    @Test
    @DisplayName("异常测试")
    void testException() {
        assertThrows(ArithmeticException.class, () -> {
            int result = 10 / 0;
        });
    }
}