package com.learning.oop;

/**
 * 子类: Cat 继承 Animal
 * <p>
 * 演示:
 * 1. 子类的构造过程会先调用父类构造
 * 2. 不同子类对父类方法的重写体现多态
 */
public class Cat extends Animal {

    private String color;

    public Cat() {
        super();
        System.out.println("[Cat] 调用了无参构造");
    }

    public Cat(String name, int age, String color) {
        super(name, age);
        this.color = color;
        System.out.println("[Cat] 调用了有参构造, color=" + color);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void climb() {
        System.out.println(getName() + " 在爬树");
    }

    /**
     * 重写父类的 makeSound 方法
     */
    @Override
    public void makeSound() {
        System.out.println(getName() + " 喵喵喵");
    }

    @Override
    public String toString() {
        return "Cat{name='" + getName() + "', age=" + getAge() + ", color='" + color + "'}";
    }
}