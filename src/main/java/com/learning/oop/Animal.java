package com.learning.oop;

/**
 * 父类: Animal
 * <p>
 * 演示面向对象的三大特性:
 * 1. 封装: 用 private 修饰字段,提供 getter/setter 访问
 * 2. 继承: 子类通过 extends 关键字继承父类
 * 3. 多态: 父类引用指向子类对象,运行时调用子类重写的方法
 */
public class Animal {

    // === 字段(成员变量) ===
    // private 修饰: 仅本类可见,体现封装性
    private String name;
    private int age;

    // static 修饰: 类变量,所有对象共享
    private static int count = 0;

    // === 构造方法 ===
    // 无参构造
    public Animal() {
        count++;
        System.out.println("[Animal] 调用了无参构造");
    }

    // 有参构造
    public Animal(String name, int age) {
        this.name = name; // this 指代当前对象
        this.age = age;
        count++;
        System.out.println("[Animal] 调用了有参构造, name=" + name + ", age=" + age);
    }

    // === 封装: getter / setter 访问私有字段 ===
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            System.out.println("年龄不能为负数");
            return;
        }
        this.age = age;
    }

    public static int getCount() {
        return count;
    }

    // === 行为方法 ===
    public void eat() {
        System.out.println(name + " 在吃东西");
    }

    public void sleep() {
        System.out.println(name + " 在睡觉");
    }

    /**
     * 叫声方法:子类会重写这个方法(多态)
     */
    public void makeSound() {
        System.out.println("动物发出声音");
    }

    @Override
    public String toString() {
        return "Animal{name='" + name + "', age=" + age + "}";
    }
}