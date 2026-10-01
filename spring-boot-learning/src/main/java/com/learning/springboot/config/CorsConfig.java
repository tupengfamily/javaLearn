package com.learning.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置(CORS)
 * <p>
 * 为什么需要 CORS:
 * - 浏览器的同源策略禁止跨域请求
 * - 前后端分离项目,前端和后端通常部署在不同端口(5173 vs 8080)
 * - 后端必须显式允许跨域,前端才能正常调用 API
 * <p>
 * 开发环境的替代方案:
 * - Vite 代理(在前端 vite.config.js 配置)
 * - 优点:无需后端改动,生产环境仍需后端 CORS 配置
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 允许的来源(生产环境应该配置具体域名,不推荐 *)
        config.addAllowedOriginPattern("*");
        // 允许的请求头
        config.addAllowedHeader("*");
        // 允许的 HTTP 方法
        config.addAllowedMethod("*");
        // 允许携带 Cookie
        config.setAllowCredentials(true);
        // 预检请求的有效期(秒)
        config.setMaxAge(3600L);
        // 暴露的响应头(前端可读取的自定义响应头)
        config.addExposedHeader("Authorization");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}