package com.learning.oop;

/**
 * 圆形: 继承 Shape 抽象类
 */
public class Circle extends Shape {

    private double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    /**
     * 实现父类的抽象方法
     */
    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }
}