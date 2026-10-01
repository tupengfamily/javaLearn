package com.learning.oop;

/**
 * 抽象类: Shape
 * <p>
 * 抽象类的特点:
 * 1. 用 abstract 修饰
 * 2. 不能被实例化
 * 3. 可以包含抽象方法(没有实现)和具体方法(有实现)
 * 4. 子类必须实现所有抽象方法(除非子类也是抽象类)
 * 5. 用于定义模板,体现"模板方法模式"
 */
public abstract class Shape {

    // 字段
    protected String color; // protected: 子类可见

    // 构造方法(用于子类调用)
    public Shape(String color) {
        this.color = color;
        System.out.println("[Shape] 构造: 颜色=" + color);
    }

    /**
     * 抽象方法: 没有方法体,子类必须重写
     */
    public abstract double area();

    /**
     * 抽象方法: 计算周长
     */
    public abstract double perimeter();

    /**
     * 具体方法: 所有子类都可以直接使用
     */
    public void display() {
        System.out.println("这是一个 " + color + " 形状, 面积=" + area() + ", 周长=" + perimeter());
    }

    public String getColor() {
        return color;
    }
}