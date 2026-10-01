package com.learning.basics;

/**
 * 控制流程语句
 * <p>
 * 三大类控制结构:
 * 1. 顺序结构: 代码从上到下依次执行(默认)
 * 2. 分支结构: if-else, switch (根据条件选择执行路径)
 * 3. 循环结构: for, while, do-while, foreach (重复执行某段代码)
 * <p>
 * 跳转语句: break, continue, return
 */
public class ControlFlow {

    public static void main(String[] args) {
        demonstrateIfElse();
        demonstrateSwitch();
        demonstrateForLoop();
        demonstrateWhileLoop();
        demonstrateJumpStatements();
    }

    /**
     * if-else 分支
     */
    public static void demonstrateIfElse() {
        System.out.println("===== if-else 分支 =====");

        int score = 85;

        // 单if
        if (score >= 60) {
            System.out.println("恭喜,及格了!");
        }

        // if-else
        if (score >= 90) {
            System.out.println("优秀");
        } else {
            System.out.println("继续保持");
        }

        // if-else if-else 多分支
        if (score >= 90) {
            System.out.println("等级: A");
        } else if (score >= 80) {
            System.out.println("等级: B");
        } else if (score >= 70) {
            System.out.println("等级: C");
        } else if (score >= 60) {
            System.out.println("等级: D");
        } else {
            System.out.println("等级: E 不及格");
        }

        System.out.println();
    }

    /**
     * switch 分支
     * <p>
     * Java 14+ 引入了新的 switch 表达式语法,更简洁安全
     */
    public static void demonstrateSwitch() {
        System.out.println("===== switch 分支 =====");

        String day = "MONDAY";

        // 传统 switch(Java 14之前)
        System.out.println("--- 传统 switch ---");
        switch (day) {
            case "MONDAY":
            case "TUESDAY":
            case "WEDNESDAY":
            case "THURSDAY":
            case "FRIDAY":
                System.out.println(day + " 是工作日");
                break;
            case "SATURDAY":
            case "SUNDAY":
                System.out.println(day + " 是周末");
                break;
            default:
                System.out.println("未知日期");
        }

        // 新版 switch 表达式(Java 14+,推荐)
        System.out.println("--- 新版 switch 表达式 ---");
        String result = switch (day) {
            case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> "工作日";
            case "SATURDAY", "SUNDAY" -> "周末";
            default -> "未知";
        };
        System.out.println(day + " → " + result);

        System.out.println();
    }

    /**
     * for 循环
     */
    public static void demonstrateForLoop() {
        System.out.println("===== for 循环 =====");

        // 传统 for 循环
        System.out.println("--- 传统 for ---");
        for (int i = 1; i <= 5; i++) {
            System.out.println("第 " + i + " 次循环");
        }

        // 增强 for 循环(foreach),用于遍历数组或集合
        System.out.println("--- foreach ---");
        int[] numbers = {10, 20, 30, 40, 50};
        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        System.out.println("数组求和: " + sum);

        System.out.println();
    }

    /**
     * while 与 do-while 循环
     */
    public static void demonstrateWhileLoop() {
        System.out.println("===== while 循环 =====");

        // while: 先判断再执行
        System.out.println("--- while ---");
        int count = 1;
        while (count <= 3) {
            System.out.println("while 第 " + count + " 次");
            count++;
        }

        // do-while: 至少执行一次
        System.out.println("--- do-while ---");
        int n = 5;
        do {
            System.out.println("do-while 执行了");
            n--;
        } while (n > 0);

        System.out.println();
    }

    /**
     * 跳转语句: break, continue
     */
    public static void demonstrateJumpStatements() {
        System.out.println("===== 跳转语句 =====");

        // break: 跳出当前循环
        System.out.println("--- break(找到第一个能被7整除的数就退出) ---");
        for (int i = 1; i <= 100; i++) {
            if (i % 7 == 0) {
                System.out.println("找到了: " + i);
                break;
            }
        }

        // continue: 跳过本次循环,继续下一次
        System.out.println("--- continue(跳过偶数) ---");
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue; // 跳过偶数
            }
            System.out.println("奇数: " + i);
        }

        // 带标签的 break(跳出多层循环)
        System.out.println("--- 带标签的 break ---");
        outer:
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (i == 2 && j == 2) {
                    System.out.println("在 i=" + i + ", j=" + j + " 处跳出");
                    break outer;
                }
                System.out.println("i=" + i + ", j=" + j);
            }
        }

        System.out.println();
    }
}