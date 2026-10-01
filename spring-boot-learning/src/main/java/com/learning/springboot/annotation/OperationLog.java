package com.learning.springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记一个 Controller 方法需要写入操作日志
 * <p>
 * 用法: @OperationLog(module = "用户管理", action = "新增")
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {

    /** 模块名,如 "用户管理" */
    String module();

    /** 操作,如 "新增" / "修改" / "删除" */
    String action() default "";

    /** 是否记录请求参数(默认 true,密码等敏感字段请在切面中过滤) */
    boolean recordParams() default true;
}