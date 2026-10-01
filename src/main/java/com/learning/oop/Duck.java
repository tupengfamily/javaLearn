package com.learning.oop;

/**
 * Duck 鸭子: 同时实现 Flyable 和 Swimmable 两个接口
 * <p>
 * 演示:
 * 1. 一个类可以实现多个接口(弥补Java单继承的不足)
 * 2. 必须实现所有接口的抽象方法
 */
public class Duck extends Animal implements Flyable, Swimmable {

    public Duck(String name, int age) {
        super(name, age);
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " 嘎嘎嘎");
    }

    /**
     * 实现 Flyable 接口的抽象方法
     */
    @Override
    public void fly() {
        System.out.println(getName() + " 在飞行, 最高 " + MAX_HEIGHT + " 米");
    }

    /**
     * 实现 Swimmable 接口的抽象方法
     */
    @Override
    public void swim() {
        System.out.println(getName() + " 在游泳");
    }

    /**
     * 重写 Flyable 的默认方法
     */
    @Override
    public void land() {
        System.out.println(getName() + " 降落到了湖面上");
    }
}