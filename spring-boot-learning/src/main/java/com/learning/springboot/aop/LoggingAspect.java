package com.learning.springboot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 日志切面(AOP)
 * <p>
 * AOP 核心概念:
 * - 切面 (Aspect): 横切关注点的模块化,这里就是 LoggingAspect
 * - 连接点 (Join Point): 程序执行过程中的点,如方法调用
 * - 通知 (Advice): 切面在特定连接点执行的动作
 * - 切点 (Pointcut): 匹配连接点的表达式
 * <p>
 * 通知类型:
 * - @Before: 前置通知(方法执行前)
 * - @After: 后置通知(方法执行后,无论是否异常)
 * - @Around: 环绕通知(可以控制方法是否执行)
 * - @AfterReturning: 返回通知(方法正常返回后)
 * - @AfterThrowing: 异常通知(方法抛出异常后)
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * 切点:匹配 com.learning.springboot.service 包下的所有方法
     */
    @Before("execution(* com.learning.springboot.service..*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("[AOP] 调用 {}.{}, 参数: {}", className, methodName, Arrays.toString(args));
    }

    /**
     * 环绕通知:可以计算方法执行耗时
     */
    @Around("execution(* com.learning.springboot.service..*.*(..))")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();

        try {
            Object result = joinPoint.proceed(); // 执行目标方法
            long elapsed = System.currentTimeMillis() - start;
            log.info("[AOP] {} 执行完成, 耗时 {} ms", methodName, elapsed);
            return result;
        } catch (Throwable e) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("[AOP] {} 执行失败, 耗时 {} ms, 异常: {}", methodName, elapsed, e.getMessage());
            throw e;
        }
    }

    /**
     * 后置通知
     */
    @After("execution(* com.learning.springboot.controller..*.*(..))")
    public void afterController(JoinPoint joinPoint) {
        log.debug("[AOP] Controller 方法执行结束: {}",
                joinPoint.getSignature().getName());
    }
}