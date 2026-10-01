package com.learning.oop;

/**
 * OOP 三大特性演示
 * <p>
 * 1. 封装: 隐藏内部细节,只暴露必要的接口
 * 2. 继承: 子类复用父类的代码
 * 3. 多态: 同一行为具有不同表现形态
 */
public class OOPDemo {

    public static void main(String[] args) {
        demonstrateEncapsulation();
        demonstrateInheritance();
        demonstratePolymorphism();
        demonstrateInstanceof();
    }

    /**
     * 封装演示
     */
    public static void demonstrateEncapsulation() {
        System.out.println("===== 封装 =====");

        Animal animal = new Animal("小动物", 3);
        // animal.name = "test"; // 编译错误,name是private
        // 必须通过 setter 访问
        animal.setName("大动物");
        animal.setAge(5);

        System.out.println("名字: " + animal.getName());
        System.out.println("年龄: " + animal.getAge());

        // setAge 中有校验逻辑
        animal.setAge(-1);
        System.out.println("尝试设置负数后, 年龄: " + animal.getAge());

        System.out.println();
    }

    /**
     * 继承演示
     */
    public static void demonstrateInheritance() {
        System.out.println("===== 继承 =====");

        Dog dog = new Dog("旺财", 4, "柴犬");
        dog.eat();        // 继承自父类
        dog.sleep();      // 继承自父类
        dog.fetch();      // 子类特有方法
        dog.makeSound();  // 子类重写的方法

        System.out.println("Dog 对象: " + dog);

        System.out.println();
    }

    /**
     * 多态演示
     * <p>
     * 多态的三个必要条件:
     * 1. 继承(或实现)
     * 2. 方法重写
     * 3. 父类引用指向子类对象
     */
    public static void demonstratePolymorphism() {
        System.out.println("===== 多态 =====");

        // 父类引用指向子类对象
        Animal a1 = new Dog("小黑", 5, "拉布拉多");
        Animal a2 = new Cat("小白", 3, "白色");

        // 编译时类型为 Animal,运行时类型分别为 Dog 和 Cat
        // 调用重写方法时,实际执行子类版本
        a1.makeSound();
        a2.makeSound();

        // 多态数组: 把多种子类对象放入父类数组统一处理
        Animal[] animals = {a1, a2, new Dog("花花", 2, "柯基")};
        for (Animal a : animals) {
            System.out.println(a.getName() + " 说: ");
            a.makeSound();
        }

        System.out.println();
    }

    /**
     * instanceof: 检查对象的实际类型
     */
    public static void demonstrateInstanceof() {
        System.out.println("===== instanceof =====");

        Animal animal = new Dog("豆豆", 4, "金毛");

        // 判断实际类型
        System.out.println("animal 是 Dog 类型? " + (animal instanceof Dog));
        System.out.println("animal 是 Cat 类型? " + (animal instanceof Cat));
        System.out.println("animal 是 Animal 类型? " + (animal instanceof Animal));

        // Java 16+ 模式匹配,转型更安全
        if (animal instanceof Dog dog) {
            dog.fetch(); // 直接用 dog 变量,无需强转
        }

        System.out.println();
    }
}