package com.learning.basics;

/**
 * 第一个Java程序 - Hello World
 * <p>
 * 学习要点:
 * 1. Java程序的基本结构(类、主方法、输出语句)
 * 2. public class 声明一个公共类,类名必须与文件名相同
 * 3. public static void main(String[] args) 是程序入口,JVM从这里开始执行
 * 4. System.out.println() 用于向控制台输出内容
 * <p>
 * 运行方式:
 * - IDE中右键点击运行
 * - 命令行: javac HelloWorld.java && java HelloWorld
 * - Maven: mvn compile exec:java -Dexec.mainClass="com.learning.basics.HelloWorld"
 */
public class HelloWorld {

    /**
     * 程序入口方法
     *
     * @param args 命令行参数,可以通过 java HelloWorld arg1 arg2 传入
     */
    public static void main(String[] args) {
        // 向控制台输出一句话
        System.out.println("Hello, World!");
        System.out.println("欢迎来到Java世界!");

        // 打印命令行参数
        if (args.length > 0) {
            System.out.println("你输入的参数是:");
            for (String arg : args) {
                System.out.println("  - " + arg);
            }
        } else {
            System.out.println("(提示: 试试在运行命令后面加上你的名字作为参数)");
        }
    }
}