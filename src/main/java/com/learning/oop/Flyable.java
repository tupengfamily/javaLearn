package com.learning.oop;

/**
 * 接口: Flyable(可飞的)
 * <p>
 * 接口的特点(Java 8+):
 * 1. 用 interface 定义
 * 2. 接口不能被实例化
 * 3. 类通过 implements 实现接口
 * 4. 一个类可以实现多个接口(多继承的体现)
 * 5. 接口中的方法默认是 public abstract(Java 8+ 可有 default 和 static 方法)
 * 6. 接口中的字段默认是 public static final(常量)
 */
public interface Flyable {

    // 接口中的常量(隐式 public static final)
    int MAX_HEIGHT = 10000; // 单位:米

    /**
     * 抽象方法(Java 8+): 默认 public abstract,可省略
     */
    void fly();

    /**
     * Java 8+ 新特性: 默认方法,有方法体,实现类可不重写
     */
    default void land() {
        System.out.println("安全着陆");
    }

    /**
     * Java 8+ 新特性: 静态方法,只能通过接口名调用
     */
    static void about() {
        System.out.println("这是一个可飞行接口");
    }
}