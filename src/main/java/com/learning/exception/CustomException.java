package com.learning.exception;

/**
 * 自定义异常
 * <p>
 * 异常的分类:
 * - 检查异常(Checked Exception): 必须显式处理,如 IOException
 * - 非检查异常(Unchecked Exception): RuntimeException 及其子类
 * <p>
 * 自定义异常通常继承 Exception 或 RuntimeException
 */
public class CustomException {

    public static void main(String[] args) {
        // 测试业务异常
        try {
            withdraw(-100);
        } catch (BusinessException e) {
            System.out.println("业务异常: " + e.getMessage());
            System.out.println("错误码: " + e.getErrorCode());
        }

        // 测试年龄验证
        try {
            validateAge(200);
        } catch (IllegalAgeException e) {
            System.out.println("年龄异常: " + e.getMessage());
        }
    }

    /**
     * 模拟提款操作
     */
    public static void withdraw(double amount) throws BusinessException {
        if (amount < 0) {
            throw new BusinessException("AMT_001", "提款金额不能为负数: " + amount);
        }
        System.out.println("提款成功: " + amount);
    }

    public static void validateAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalAgeException("年龄不合法: " + age + ", 应在 0~150 之间");
        }
        System.out.println("年龄合法: " + age);
    }
}

/**
 * 业务异常(检查异常)
 */
class BusinessException extends Exception {
    private final String errorCode;

    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

/**
 * 年龄不合法异常(运行时异常)
 */
class IllegalAgeException extends RuntimeException {
    public IllegalAgeException(String message) {
        super(message);
    }
}