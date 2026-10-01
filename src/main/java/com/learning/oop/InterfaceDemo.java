package com.learning.oop;

/**
 * 接口与抽象类演示
 */
public class InterfaceDemo {

    public static void main(String[] args) {
        demonstrateAbstractClass();
        demonstrateInterface();
        demonstrateMultipleInterfaces();
        demonstrateInterfaceStatic();
    }

    /**
     * 抽象类演示
     */
    public static void demonstrateAbstractClass() {
        System.out.println("===== 抽象类 =====");

        // Shape shape = new Shape(); // 编译错误,抽象类不能实例化
        Shape circle = new Circle("红色", 5.0);
        Shape rectangle = new Rectangle("蓝色", 4.0, 6.0);

        circle.display();
        rectangle.display();

        System.out.println();
    }

    /**
     * 接口演示
     */
    public static void demonstrateInterface() {
        System.out.println("===== 接口 =====");

        Duck duck = new Duck("唐老鸭", 5);
        duck.eat();     // 继承自 Animal
        duck.fly();     // 实现自 Flyable
        duck.swim();    // 实现自 Swimmable
        duck.dive();    // Swimmable 的默认方法
        duck.land();    // Flyable 的默认方法被重写

        System.out.println();
    }

    /**
     * 多接口实现演示
     */
    public static void demonstrateMultipleInterfaces() {
        System.out.println("===== 多接口 =====");

        // 多态: 接口引用指向实现类对象
        Flyable flyer = new Duck("丑小鸭", 1);
        flyer.fly();

        Swimmable swimmer = new Duck("丑小鸭", 1);
        swimmer.swim();

        // 同时是多个接口的实现
        Duck duck = (Duck) flyer;
        if (swimmer instanceof Duck sameDuck) {
            sameDuck.makeSound();
        }

        System.out.println();
    }

    /**
     * 接口静态方法
     */
    public static void demonstrateInterfaceStatic() {
        System.out.println("===== 接口静态方法 =====");
        Flyable.about(); // 通过接口名直接调用
        System.out.println("最大飞行高度: " + Flyable.MAX_HEIGHT + " 米");
        System.out.println();
    }
}