package com.learning.oop;

/**
 * 多态深入演示
 * <p>
 * 多态的本质:
 * - 编译看左边(类型): Animal animal = new Dog();
 * - 运行看右边(对象): 调用方法时,实际执行 Dog 的方法
 * <p>
 * 多态的常见应用:
 * 1. 父类型作为方法形参,实参可以是任意子类
 * 2. 父类型作为返回值,实际可以返回任意子类
 */
public class PolymorphismDemo {

    public static void main(String[] args) {
        // 场景1: 形参使用父类型
        AnimalFeeder feeder = new AnimalFeeder();
        Animal dog = new Dog("旺财", 4, "柴犬");
        Animal cat = new Cat("小花", 3, "橘色");
        feeder.feed(dog);
        feeder.feed(cat);

        // 场景2: 返回值使用父类型
        AnimalFactory factory = new AnimalFactory();
        Animal animal = factory.create("dog", "小黑", 2);
        animal.makeSound(); // 实际调用 Dog 的方法

        // 场景3: 多态数组
        Animal[] animals = {
            new Dog("A", 1, "哈士奇"),
            new Cat("B", 2, "黑色"),
            new Duck("C", 3)
        };

        System.out.println("--- 统一处理 ---");
        for (Animal a : animals) {
            a.eat();
            a.makeSound();
        }
    }
}

/**
 * 动物喂食器:接收父类引用,处理所有子类对象
 */
class AnimalFeeder {
    public void feed(Animal animal) {
        System.out.println("正在喂 " + animal.getName());
        animal.eat();
    }
}

/**
 * 动物工厂:返回父类类型,实际可以是任意子类
 */
class AnimalFactory {
    public Animal create(String type, String name, int age) {
        return switch (type.toLowerCase()) {
            case "dog" -> new Dog(name, age, "未知品种");
            case "cat" -> new Cat(name, age, "未知颜色");
            case "duck" -> new Duck(name, age);
            default -> throw new IllegalArgumentException("未知类型: " + type);
        };
    }
}