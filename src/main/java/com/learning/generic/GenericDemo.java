package com.learning.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型(Generics)
 * <p>
 * 作用: 在编译期检查类型,避免运行时类型转换异常,提高代码复用性
 * <p>
 * 用法:
 * 1. 泛型类/接口: class Box&lt;T&gt;
 * 2. 泛型方法: &lt;T&gt; T method(T t)
 * 3. 通配符: ? extends Number(上界), ? super Integer(下界)
 */
public class GenericDemo {

    public static void main(String[] args) {
        demonstrateWhyGeneric();
        demonstrateGenericClass();
        demonstrateGenericMethod();
        demonstrateWildcard();
        demonstrateTypeErasure();
    }

    /**
     * 为什么需要泛型
     */
    public static void demonstrateWhyGeneric() {
        System.out.println("===== 为什么需要泛型 =====");

        // 没有泛型时,集合可以放任何类型,取出时需要强转
        List list = new ArrayList();
        list.add("hello");
        list.add(123); // 编译期不报错

        // 取出时类型转换错误
        try {
            String s = (String) list.get(1);
        } catch (ClassCastException e) {
            System.out.println("没有泛型会出错: " + e.getMessage());
        }

        // 使用泛型后,编译器会保证类型安全
        List<String> safeList = new ArrayList<>();
        safeList.add("Java");
        // safeList.add(123); // 编译错误
        String s = safeList.get(0); // 无需强转
        System.out.println("安全取出: " + s);

        System.out.println();
    }

    /**
     * 自定义泛型类
     */
    public static void demonstrateGenericClass() {
        System.out.println("===== 泛型类 =====");

        Box<String> stringBox = new Box<>();
        stringBox.set("Hello");
        System.out.println("字符串盒子: " + stringBox.get());

        Box<Integer> intBox = new Box<>();
        intBox.set(100);
        System.out.println("整数盒子: " + intBox.get());

        // 多个类型参数
        Pair<String, Integer> pair = new Pair<>("年龄", 25);
        System.out.println("键值对: " + pair);

        System.out.println();
    }

    /**
     * 泛型方法
     */
    public static void demonstrateGenericMethod() {
        System.out.println("===== 泛型方法 =====");

        Integer[] intArr = {1, 2, 3, 4, 5};
        String[] strArr = {"A", "B", "C"};

        System.out.println("整数数组: " + arrayToString(intArr));
        System.out.println("字符串数组: " + arrayToString(strArr));

        System.out.println();
    }

    /**
     * 泛型方法: 独立的类型参数声明
     */
    public static <T> String arrayToString(T[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(array[i]);
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 通配符: ? 用于灵活的泛型
     * <p>
     * ? extends T: 上界通配,只能读,不能写(null除外)
     * ? super T: 下界通配,可以写入 T 或其子类
     */
    public static void demonstrateWildcard() {
        System.out.println("===== 通配符 =====");

        List<Integer> intList = List.of(1, 2, 3);
        List<Double> doubleList = List.of(1.1, 2.2, 3.3);
        List<Number> numList = new ArrayList<>();

        // 上界通配: 接收 Number 或其子类
        printNumbers(intList);
        printNumbers(doubleList);

        // 下界通配: 只能传入 Number 或其父类
        addNumbers(numList);
        System.out.println("添加后: " + numList);

        System.out.println();
    }

    /**
     * 上界通配
     */
    public static void printNumbers(List<? extends Number> list) {
        for (Number n : list) {
            System.out.println("数字: " + n);
        }
    }

    /**
     * 下界通配
     */
    public static void addNumbers(List<? super Number> list) {
        list.add(1);
        list.add(2.0);
    }

    /**
     * 类型擦除: 编译期类型检查,运行期类型擦除
     * <p>
     * List<String> 和 List<Integer> 在 JVM 中都是 List
     */
    public static void demonstrateTypeErasure() {
        System.out.println("===== 类型擦除 =====");

        List<String> strList = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();

        // 编译期认为类型不同,运行期其实是同一个类
        System.out.println("String List 类: " + strList.getClass().getName());
        System.out.println("Integer List 类: " + intList.getClass().getName());
        System.out.println("是否同一个类: " + (strList.getClass() == intList.getClass()));

        System.out.println();
    }
}

/**
 * 简单泛型类
 */
class Box<T> {
    private T content;

    public void set(T content) {
        this.content = content;
    }

    public T get() {
        return content;
    }
}

/**
 * 多类型参数的泛型类
 */
class Pair<K, V> {
    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return key; }
    public V getValue() { return value; }

    @Override
    public String toString() {
        return key + " = " + value;
    }
}