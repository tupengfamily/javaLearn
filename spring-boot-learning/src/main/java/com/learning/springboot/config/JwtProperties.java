package com.learning.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT 配置属性
 * <p>
 * 通过 @EnableConfigurationProperties 注册到 Spring 上下文。
 */
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /** Base64 编码的 HMAC 密钥(>= 32 字节) */
    private String secret;

    /** Token 过期时间(毫秒) */
    private long expirationMillis = 86400000L;  // 默认 24 小时

    /** HTTP 请求头名称 */
    private String header = "Authorization";

    /** Token 前缀 */
    private String prefix = "Bearer ";

    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }

    public long getExpirationMillis() { return expirationMillis; }
    public void setExpirationMillis(long expirationMillis) { this.expirationMillis = expirationMillis; }

    public String getHeader() { return header; }
    public void setHeader(String header) { this.header = header; }

    public String getPrefix() { return prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }
}