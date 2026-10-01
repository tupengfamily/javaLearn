package com.learning.oop;

/**
 * 接口: Swimmable(可游泳的)
 * <p>
 * 演示一个类实现多个接口
 */
public interface Swimmable {
    void swim();

    default void dive() {
        System.out.println("潜入水中");
    }
}