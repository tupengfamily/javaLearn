package com.learning.basics;

/**
 * 方法(Method)
 * <p>
 * 方法是组织好的、可重复使用的代码块,用于执行特定任务。
 * <p>
 * 语法: [修饰符] 返回类型 方法名(参数列表) [throws 异常] { 方法体 }
 * <p>
 * 重点概念:
 * 1. 方法签名: 方法名 + 参数列表(返回类型不属于签名)
 * 2. 方法重载(Overload): 同名不同参
 * 3. 方法参数: 值传递(基本类型传值,引用类型传地址)
 * 4. 变长参数: ...
 */
public class MethodsDemo {

    public static void main(String[] args) {
        // 调用静态方法: 直接通过 类名.方法名 或 方法名 调用
        int sum = add(3, 5);
        System.out.println("3 + 5 = " + sum);

        // 调用重载方法
        System.out.println("两个数相加: " + add(1, 2));
        System.out.println("三个数相加: " + add(1, 2, 3));
        System.out.println("小数相加: " + add(1.5, 2.5));

        // 调用带返回值的方法
        int factorial = factorial(5);
        System.out.println("5! = " + factorial);

        // 调用变长参数方法
        printNumbers(1, 2, 3, 4, 5);
        printNumbers(); // 可以不传

        // 演示参数传递
        int num = 10;
        changePrimitive(num);
        System.out.println("基本类型传递后 num = " + num); // 仍是 10

        int[] arr = {1, 2, 3};
        changeArray(arr);
        System.out.println("数组传递后第一个元素: " + arr[0]); // 变成 999
    }

    /**
     * 最简单的静态方法: 两个整数相加
     */
    public static int add(int a, int b) {
        return a + b;
    }

    /**
     * 方法重载: 参数个数不同
     */
    public static int add(int a, int b, int c) {
        return a + b + c;
    }

    /**
     * 方法重载: 参数类型不同
     */
    public static double add(double a, double b) {
        return a + b;
    }

    /**
     * 递归: 求阶乘
     */
    public static int factorial(int n) {
        if (n <= 1) {
            return 1; // 递归终止条件
        }
        return n * factorial(n - 1);
    }

    /**
     * 变长参数(本质是数组)
     * 一个方法最多只能有一个变长参数,且必须放在最后
     */
    public static void printNumbers(int... numbers) {
        System.out.print("变长参数: ");
        for (int n : numbers) {
            System.out.print(n + " ");
        }
        System.out.println();
    }

    /**
     * 基本类型参数: 值传递,方法内修改不影响外部
     */
    public static void changePrimitive(int num) {
        num = 100; // 只是修改了副本
    }

    /**
     * 引用类型参数: 传递的是地址,方法内修改会影响外部
     */
    public static void changeArray(int[] arr) {
        if (arr != null && arr.length > 0) {
            arr[0] = 999;
        }
    }
}