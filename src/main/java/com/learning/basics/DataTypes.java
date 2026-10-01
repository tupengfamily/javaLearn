package com.learning.basics;

/**
 * Java数据类型详解
 * <p>
 * Java是强类型语言,每个变量都必须声明类型。
 * 数据类型分为两大类:
 * 1. 基本数据类型(8种): byte, short, int, long, float, double, char, boolean
 * 2. 引用数据类型: 类、接口、数组、字符串等
 */
public class DataTypes {

    public static void main(String[] args) {
        demonstratePrimitiveTypes();
        demonstrateReferenceTypes();
        demonstrateTypeCasting();
        demonstrateAutoBoxing();
    }

    /**
     * 基本数据类型
     */
    public static void demonstratePrimitiveTypes() {
        System.out.println("===== 基本数据类型 =====");

        // 整数类型
        byte byteNum = 100;          // 1字节,范围 -128 ~ 127
        short shortNum = 30000;      // 2字节,范围 -32768 ~ 32767
        int intNum = 2_000_000_000;  // 4字节,约 ±21亿(数字下划线增强可读性)
        long longNum = 9_000_000_000_000L; // 8字节,长整型需加L后缀

        System.out.println("byte: " + byteNum);
        System.out.println("short: " + shortNum);
        System.out.println("int: " + intNum);
        System.out.println("long: " + longNum);

        // 浮点类型
        float floatNum = 3.14f;      // 4字节,需加f后缀
        double doubleNum = 3.141592653589793; // 8字节,精度更高
        System.out.println("float: " + floatNum);
        System.out.println("double: " + doubleNum);

        // 字符类型
        char ch = 'A';               // 2字节,使用单引号,存储Unicode字符
        char chineseChar = '中';     // 可以存储中文
        System.out.println("char: " + ch + ", 中文: " + chineseChar);

        // 布尔类型
        boolean isJavaFun = true;
        boolean isFishTasty = false;
        System.out.println("boolean: " + isJavaFun + ", " + isFishTasty);

        System.out.println();
    }

    /**
     * 引用数据类型 - String 是最常用的引用类型
     */
    public static void demonstrateReferenceTypes() {
        System.out.println("===== 引用数据类型 =====");

        // 字符串(注意使用双引号)
        String name = "张三";
        String greeting = "你好," + name + "!"; // 字符串拼接
        System.out.println(greeting);

        // 数组(引用类型)
        int[] scores = {95, 87, 76, 100, 88};
        System.out.println("数组长度: " + scores.length);
        System.out.println("第一个元素: " + scores[0]);

        // 字符串常用方法
        String text = "  Hello Java  ";
        System.out.println("原字符串: '" + text + "'");
        System.out.println("长度: " + text.length());
        System.out.println("去空格: '" + text.trim() + "'");
        System.out.println("大写: " + text.toUpperCase());
        System.out.println("包含Java: " + text.contains("Java"));

        System.out.println();
    }

    /**
     * 类型转换
     */
    public static void demonstrateTypeCasting() {
        System.out.println("===== 类型转换 =====");

        // 自动类型转换(小类型 → 大类型)
        int intVal = 100;
        long longVal = intVal;       // 自动转换
        double doubleVal = longVal;  // 自动转换
        System.out.println("自动转换: int → long → double: " + doubleVal);

        // 强制类型转换(大类型 → 小类型,可能丢失精度)
        double pi = 3.14159;
        int intPi = (int) pi;        // 强制转换,小数部分被截断
        System.out.println("强制转换: " + pi + " → " + intPi);

        System.out.println();
    }

    /**
     * 自动装箱与拆箱(Java 5+)
     * <p>
     * 装箱: 基本类型 → 包装类
     * 拆箱: 包装类 → 基本类型
     */
    public static void demonstrateAutoBoxing() {
        System.out.println("===== 自动装箱与拆箱 =====");

        // 装箱
        Integer intObj = 100;        // 自动装箱
        Double doubleObj = 3.14;     // 自动装箱

        // 拆箱
        int intVal = intObj;         // 自动拆箱
        double doubleVal = doubleObj;

        System.out.println("Integer对象: " + intObj);
        System.out.println("自动拆箱后: " + intVal);

        // 包装类的常用方法
        System.out.println("字符串转int: " + Integer.parseInt("123"));
        System.out.println("int最大值: " + Integer.MAX_VALUE);
        System.out.println("int最小值: " + Integer.MIN_VALUE);

        System.out.println();
    }
}