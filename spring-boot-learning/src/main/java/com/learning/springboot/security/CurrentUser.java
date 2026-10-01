package com.learning.springboot.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注入当前登录用户信息的注解
 * <p>
 * 用法: 在 Controller 方法参数上加 @CurrentUser
 * 解析: 由 CurrentUserResolver 实现
 * - Long userId
 * - String username
 * - Set&lt;String&gt; roles
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
    /**
     * 要注入的属性: "userId" / "username" / "roles"
     */
    String value() default "userId";
}