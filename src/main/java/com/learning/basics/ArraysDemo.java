package com.learning.basics;

import java.util.Arrays;

/**
 * 数组(Array)
 * <p>
 * 数组是相同类型数据的有序集合,长度固定。
 * 特点:
 * 1. 长度在创建时确定,之后不能改变
 * 2. 元素类型必须相同(可以存放基本类型或引用类型)
 * 3. 通过下标访问元素,从 0 开始
 * 4. 数组本身是引用类型,存放在堆内存中
 */
public class ArraysDemo {

    public static void main(String[] args) {
        declareAndInitialize();
        iterateArray();
        arrayOperations();
        multidimensionalArray();
        commonPitfalls();
    }

    /**
     * 数组的声明与初始化
     */
    public static void declareAndInitialize() {
        System.out.println("===== 数组声明与初始化 =====");

        // 方式1: 先声明后分配空间
        int[] arr1 = new int[5]; // 默认初始化为 0
        arr1[0] = 10;
        arr1[1] = 20;
        System.out.println("arr1: " + Arrays.toString(arr1));

        // 方式2: 声明并直接初始化(常用)
        int[] arr2 = {1, 2, 3, 4, 5};
        System.out.println("arr2: " + Arrays.toString(arr2));

        // 方式3: 使用 new 关键字初始化
        String[] names = new String[]{"张三", "李四", "王五"};
        System.out.println("names: " + Arrays.toString(names));

        // 数组长度通过 .length 访问(注意不是方法)
        System.out.println("arr2 长度: " + arr2.length);

        System.out.println();
    }

    /**
     * 数组的遍历
     */
    public static void iterateArray() {
        System.out.println("===== 数组遍历 =====");

        int[] scores = {95, 87, 76, 100, 88};

        // 方式1: 普通 for 循环
        System.out.println("--- 普通 for ---");
        for (int i = 0; i < scores.length; i++) {
            System.out.println("scores[" + i + "] = " + scores[i]);
        }

        // 方式2: 增强 for 循环(foreach)
        System.out.println("--- foreach ---");
        for (int score : scores) {
            System.out.println("分数: " + score);
        }

        // 方式3: Arrays.toString()
        System.out.println("--- Arrays.toString ---");
        System.out.println(Arrays.toString(scores));

        System.out.println();
    }

    /**
     * 常用数组操作
     */
    public static void arrayOperations() {
        System.out.println("===== 数组操作 =====");

        int[] arr = {5, 3, 8, 1, 9, 2, 7};

        // 排序
        int[] sorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(sorted);
        System.out.println("排序后: " + Arrays.toString(sorted));

        // 二分查找(必须先排序)
        int index = Arrays.binarySearch(sorted, 8);
        System.out.println("8 在排序后数组中的下标: " + index);

        // 填充
        int[] filled = new int[5];
        Arrays.fill(filled, 99);
        System.out.println("填充后: " + Arrays.toString(filled));

        // 比较
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};
        System.out.println("数组相等: " + Arrays.equals(a, b));

        // 拷贝
        int[] original = {1, 2, 3, 4, 5};
        int[] copied = Arrays.copyOfRange(original, 1, 4); // 拷贝 [1,4)
        System.out.println("拷贝 [1,4): " + Arrays.toString(copied));

        // 求最大最小
        int max = Arrays.stream(arr).max().getAsInt();
        int min = Arrays.stream(arr).min().getAsInt();
        System.out.println("最大值: " + max + ", 最小值: " + min);

        System.out.println();
    }

    /**
     * 多维数组(以二维为例)
     */
    public static void multidimensionalArray() {
        System.out.println("===== 二维数组 =====");

        // 3x3 的矩阵
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        // 遍历二维数组
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        // foreach 遍历
        System.out.println("--- foreach 遍历 ---");
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }

        // 使用 Arrays.deepToString 打印二维数组
        System.out.println("矩阵: " + Arrays.deepToString(matrix));

        System.out.println();
    }

    /**
     * 常见陷阱
     */
    public static void commonPitfalls() {
        System.out.println("===== 常见陷阱 =====");

        // 陷阱1: 数组下标越界
        int[] arr = {1, 2, 3};
        try {
            System.out.println(arr[5]); // 抛出 ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("下标越界异常: " + e.getMessage());
        }

        // 陷阱2: 空指针异常
        int[] nullArr = null;
        try {
            System.out.println(nullArr.length);
        } catch (NullPointerException e) {
            System.out.println("空指针异常: " + e.getMessage());
        }

        System.out.println();
    }
}