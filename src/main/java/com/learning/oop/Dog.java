package com.learning.oop;

/**
 * 子类: Dog 继承 Animal
 * <p>
 * 演示继承的语法:
 * 1. 使用 extends 关键字
 * 2. Java是单继承,一个类只能直接继承一个父类
 * 3. 子类自动拥有父类的非私有成员
 * 4. 子类可以添加自己的新字段和方法
 */
public class Dog extends Animal {

    // 子类特有的字段
    private String breed; // 品种

    public Dog() {
        super(); // 调用父类的无参构造(默认会隐式调用)
        System.out.println("[Dog] 调用了无参构造");
    }

    public Dog(String name, int age, String breed) {
        super(name, age); // 调用父类的有参构造
        this.breed = breed;
        System.out.println("[Dog] 调用了有参构造, breed=" + breed);
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    // 子类特有方法
    public void fetch() {
        System.out.println(getName() + " 在捡球");
    }

    /**
     * 方法重写(Override): 子类改写父类的方法
     * 必须满足:
     * 1. 方法名和参数列表完全相同
     * 2. 返回类型相同或是其子类
     * 3. 访问修饰符不能更严格
     * 4. 不能抛出比父类更多的异常
     */
    @Override
    public void makeSound() {
        super.makeSound(); // 可选: 调用父类的方法
        System.out.println(getName() + " 汪汪汪");
    }

    /**
     * 重写 toString
     */
    @Override
    public String toString() {
        return "Dog{name='" + getName() + "', age=" + getAge() + ", breed='" + breed + "'}";
    }
}