package com.learning.springboot.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 * <p>
 * 演示 @Component 注册为 Bean,可以直接通过 @Autowired 注入。
 */
@Component
public class DateUtil {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public String format(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN));
    }

    public String format(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public LocalDateTime parse(String text) {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(DEFAULT_PATTERN));
    }

    public String now() {
        return format(LocalDateTime.now());
    }
}