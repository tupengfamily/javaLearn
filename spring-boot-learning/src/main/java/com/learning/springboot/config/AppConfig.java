package com.learning.springboot.config;

import com.learning.springboot.util.DateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 应用配置类
 * <p>
 * @Configuration 标识这是一个配置类,相当于以前的 XML 配置。
 * <p>
 * @Bean 注解的方法会返回一个 Bean,由 Spring 容器管理。
 */
@Configuration
public class AppConfig {

    /**
     * 把 DateUtil 注册为 Spring 容器中的 Bean
     * <p>
     * 这样在其他组件中可以通过 @Autowired 注入使用。
     */
    @Bean
    public DateUtil dateUtil() {
        return new DateUtil();
    }
}