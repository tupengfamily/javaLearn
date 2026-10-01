package com.learning.exception;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * 异常处理
 * <p>
 * Java 异常体系:
 * - Throwable (根)
 *   - Error: 严重错误,程序无法处理,如 OutOfMemoryError
 *   - Exception
 *     - RuntimeException: 运行时异常(unchecked)
 *     - 其他 Exception: 必须显式处理(checked)
 * <p>
 * 关键字: try, catch, finally, throw, throws
 * try-with-resources(Java 7+): 自动关闭资源
 */
public class ExceptionDemo {

    public static void main(String[] args) {
        demonstrateTryCatch();
        demonstrateMultipleCatch();
        demonstrateFinally();
        demonstrateThrowVsThrows();
        demonstrateTryWithResources();
    }

    /**
     * 基本 try-catch
     */
    public static void demonstrateTryCatch() {
        System.out.println("===== 基本 try-catch =====");

        try {
            int result = 10 / 0; // 算术异常
            System.out.println("结果: " + result);
        } catch (ArithmeticException e) {
            System.out.println("捕获到算术异常: " + e.getMessage());
        }

        System.out.println("程序继续执行...");
        System.out.println();
    }

    /**
     * 多重 catch: 注意子类必须排在父类前面
     */
    public static void demonstrateMultipleCatch() {
        System.out.println("===== 多重 catch =====");

        try {
            String s = null;
            // s.length(); // NullPointerException
            int[] arr = {1, 2, 3};
            System.out.println(arr[5]); // ArrayIndexOutOfBoundsException
        } catch (NullPointerException e) {
            System.out.println("空指针: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("下标越界: " + e.getMessage());
        } catch (Exception e) {
            // 兜底,捕获所有异常
            System.out.println("其他异常: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * finally: 无论是否发生异常,都会执行
     */
    public static void demonstrateFinally() {
        System.out.println("===== finally =====");

        try {
            System.out.println("try 中的语句");
            int x = 1 / 0;
        } catch (ArithmeticException e) {
            System.out.println("catch 中: " + e.getMessage());
            return; // 即便 return, finally 也会执行
        } finally {
            System.out.println("finally 中的语句总会执行");
        }

        System.out.println();
    }

    /**
     * throw vs throws
     * <p>
     * throw: 在方法内部抛出异常对象
     * throws: 在方法声明上声明可能抛出的异常类型
     */
    public static void demonstrateThrowVsThrows() {
        System.out.println("===== throw vs throws =====");

        try {
            readFile("不存在的文件.txt");
        } catch (FileNotFoundException e) {
            System.out.println("捕获方法声明的异常: " + e.getMessage());
        }

        try {
            divide(10, 0);
        } catch (ArithmeticException e) {
            System.out.println("捕获抛出的异常: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * throws 关键字: 声明方法可能抛出的异常
     */
    public static void readFile(String path) throws FileNotFoundException {
        FileReader reader = new FileReader(path); // 可能抛出 FileNotFoundException
    }

    /**
     * throw 关键字: 主动抛出异常
     */
    public static int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("除数不能为0");
        }
        return a / b;
    }

    /**
     * try-with-resources: 自动关闭资源(实现了 AutoCloseable 接口的对象)
     */
    public static void demonstrateTryWithResources() {
        System.out.println("===== try-with-resources =====");

        // 传统方式: 需要在 finally 中手动关闭
        // try-with-resources 自动关闭,代码更简洁
        try (FileReader reader = new FileReader("pom.xml")) {
            System.out.println("成功打开 pom.xml");
            int ch;
            int count = 0;
            while ((ch = reader.read()) != -1) {
                count++;
            }
            System.out.println("文件总字符数: " + count);
        } catch (Exception e) {
            System.out.println("读取文件失败: " + e.getMessage());
        }

        // try-with-resources 可以同时管理多个资源
        // try (var r1 = ...; var r2 = ...) { ... }

        System.out.println();
    }
}