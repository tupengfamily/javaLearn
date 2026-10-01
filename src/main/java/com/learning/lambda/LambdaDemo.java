package com.learning.lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Lambda 表达式(Java 8+)
 * <p>
 * Lambda 是一种匿名函数,可以让代码更简洁。
 * <p>
 * 语法: (参数) -> { 方法体 }
 * <p>
 * 函数式接口: 只有一个抽象方法的接口
 * Java 8 内置的函数式接口都在 java.util.function 包下:
 * - Supplier: 无参有返回值 T get()
 * - Consumer: 有参无返回值 void accept(T t)
 * - Predicate: 有参返回 boolean boolean test(T t)
 * - Function: 有参有返回值 R apply(T t)
 * - BiFunction, BiConsumer, BiPredicate 等
 */
public class LambdaDemo {

    public static void main(String[] args) {
        demonstrateBasicLambda();
        demonstrateMethodReference();
        demonstrateFunctionalInterfaces();
        demonstrateComparatorWithLambda();
    }

    /**
     * 基本 Lambda
     */
    public static void demonstrateBasicLambda() {
        System.out.println("===== 基本 Lambda =====");

        // 1. 无参无返回值
        Runnable r1 = () -> System.out.println("我是 Runnable");
        r1.run();

        // 2. 一个参数
        Consumer<String> c1 = s -> System.out.println("你好, " + s);
        c1.accept("Lambda");

        // 3. 两个参数
        java.util.function.BiConsumer<Integer, Integer> bc = (a, b) -> System.out.println(a + " + " + b + " = " + (a + b));
        bc.accept(3, 5);

        // 4. 有返回值
        Supplier<Double> s1 = () -> Math.random();
        System.out.println("随机数: " + s1.get());

        // 6. 带类型(显式声明)
        BinaryOperator<Integer> add = (Integer a, Integer b) -> a + b;
        System.out.println("自定义函数: 3 + 5 = " + add.apply(3, 5));

        System.out.println();
    }

    /**
     * 方法引用(::)
     * <p>
     * - 对象::方法名
     * - 类::静态方法名
     * - 类::实例方法名(第一个参数作为调用者)
     * - 类::new(构造引用)
     */
    public static void demonstrateMethodReference() {
        System.out.println("===== 方法引用 =====");

        List<String> names = new ArrayList<>(List.of("Charlie", "Alice", "Bob"));

        // 静态方法引用
        names.sort(String::compareTo); // 等价于 (a, b) -> a.compareTo(b)
        System.out.println("排序后: " + names);

        // 实例方法引用
        names.forEach(System.out::println); // 等价于 s -> System.out.println(s)

        // 构造引用
        java.util.function.Function<String, StringBuilder> sbCreator = StringBuilder::new;
        StringBuilder sb = sbCreator.apply("Hello");
        sb.append(" World");
        System.out.println("构造引用: " + sb);

        System.out.println();
    }

    /**
     * 内置函数式接口
     */
    public static void demonstrateFunctionalInterfaces() {
        System.out.println("===== 函数式接口 =====");

        // Predicate: 断言
        Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println("4 是偶数? " + isEven.test(4));

        Predicate<Integer> isPositive = n -> n > 0;
        Predicate<Integer> isPositiveEven = isPositive.and(isEven); // 组合
        System.out.println("6 是正偶数? " + isPositiveEven.test(6));

        // Function: 转换
        Function<Integer, String> intToStr = n -> "数字: " + n;
        System.out.println(intToStr.apply(100));

        Function<Integer, Integer> multiply2 = n -> n * 2;
        Function<Integer, Integer> add1 = n -> n + 1;
        Function<Integer, Integer> combined = multiply2.andThen(add1); // 先乘2再加1
        System.out.println("组合运算 5 → ×2 → +1 = " + combined.apply(5));

        // Consumer: 消费
        Consumer<String> print = System.out::println;
        Consumer<String> greet = s -> System.out.println("你好, " + s);
        print.andThen(greet).accept("Lambda");

        // Supplier: 提供
        Supplier<Double> random = Math::random;
        System.out.println("随机数: " + random.get());

        System.out.println();
    }

    /**
     * Comparator 用 Lambda
     */
    public static void demonstrateComparatorWithLambda() {
        System.out.println("===== Comparator + Lambda =====");

        List<Person> people = new ArrayList<>(List.of(
            new Person("张三", 25),
            new Person("李四", 30),
            new Person("王五", 20)
        ));

        // 按年龄排序
        people.sort(Comparator.comparingInt(Person::getAge));
        System.out.println("按年龄升序: " + people);

        // 按年龄降序
        people.sort(Comparator.comparingInt(Person::getAge).reversed());
        System.out.println("按年龄降序: " + people);

        // 按姓名长度排序
        people.sort(Comparator.comparingInt(p -> p.getName().length()));
        System.out.println("按姓名长度: " + people);

        System.out.println();
    }
}

/**
 * 自定义函数式接口
 */
@java.lang.FunctionalInterface
interface BinaryOperator<T> {
    T apply(T a, T b);
}

/**
 * 用于测试的 Person 类
 */
class Person {
    private String name;
    private int age;
    private String city;

    public Person(String name, int age) {
        this(name, age, "未知");
    }

    public Person(String name, int age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCity() { return city; }

    @Override
    public String toString() {
        return name + "(" + age + ", " + city + ")";
    }
}