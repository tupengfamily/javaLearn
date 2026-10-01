package com.learning.basics;

/**
 * Java运算符
 * <p>
 * 运算符分类:
 * 1. 算术运算符: +, -, *, /, %, ++, --
 * 2. 赋值运算符: =, +=, -=, *=, /=, %=
 * 3. 比较运算符: ==, !=, >, <, >=, <=
 * 4. 逻辑运算符: &&, ||, !, &, |
 * 5. 位运算符: &, |, ^, ~, <<, >>, >>>
 * 6. 三元运算符: ? :
 */
public class Operators {

    public static void main(String[] args) {
        demonstrateArithmetic();
        demonstrateAssignment();
        demonstrateComparison();
        demonstrateLogical();
        demonstrateBitwise();
        demonstrateTernary();
    }

    /**
     * 算术运算符
     */
    public static void demonstrateArithmetic() {
        System.out.println("===== 算术运算符 =====");

        int a = 10, b = 3;

        System.out.println("a + b = " + (a + b));   // 13
        System.out.println("a - b = " + (a - b));   // 7
        System.out.println("a * b = " + (a * b));   // 30
        System.out.println("a / b = " + (a / b));   // 3 (整数除法)
        System.out.println("a % b = " + (a % b));   // 1 (取余)

        // 自增自减
        int x = 5;
        System.out.println("x++ = " + (x++)); // 5, 先取值后自增
        System.out.println("x = " + x);       // 6
        System.out.println("++x = " + (++x)); // 7, 先自增后取值
        System.out.println("x-- = " + (x--)); // 7
        System.out.println("--x = " + (--x)); // 5

        System.out.println();
    }

    /**
     * 赋值运算符
     */
    public static void demonstrateAssignment() {
        System.out.println("===== 赋值运算符 =====");

        int x = 10;
        System.out.println("初始 x = " + x);

        x += 5;  // 等价于 x = x + 5
        System.out.println("x += 5 → " + x);

        x -= 3;
        System.out.println("x -= 3 → " + x);

        x *= 2;
        System.out.println("x *= 2 → " + x);

        x /= 4;
        System.out.println("x /= 4 → " + x);

        x %= 3;
        System.out.println("x %= 3 → " + x);

        System.out.println();
    }

    /**
     * 比较运算符(返回boolean)
     */
    public static void demonstrateComparison() {
        System.out.println("===== 比较运算符 =====");

        int a = 10, b = 20;

        System.out.println("a == b: " + (a == b)); // false
        System.out.println("a != b: " + (a != b)); // true
        System.out.println("a > b:  " + (a > b));  // false
        System.out.println("a < b:  " + (a < b));  // true
        System.out.println("a >= b: " + (a >= b)); // false
        System.out.println("a <= b: " + (a <= b)); // true

        System.out.println();
    }

    /**
     * 逻辑运算符
     * && 和 || 具有短路特性,左边能确定结果时,右边不执行
     */
    public static void demonstrateLogical() {
        System.out.println("===== 逻辑运算符 =====");

        boolean a = true, b = false;

        System.out.println("a && b: " + (a && b)); // 逻辑与,都为真才为真
        System.out.println("a || b: " + (a || b)); // 逻辑或,有一个为真就为真
        System.out.println("!a:     " + (!a));     // 逻辑非

        // 短路示例
        int x = 5;
        boolean result = (x > 0) && (x++ > 0);
        System.out.println("短路与: result = " + result + ", x = " + x); // x = 6

        boolean result2 = (x < 0) && (x++ > 0);
        System.out.println("短路与: result2 = " + result2 + ", x = " + x); // x 仍为 6,因为第二个条件未执行

        System.out.println();
    }

    /**
     * 位运算符(直接操作二进制位,效率高)
     */
    public static void demonstrateBitwise() {
        System.out.println("===== 位运算符 =====");

        int a = 5;  // 二进制: 0101
        int b = 3;  // 二进制: 0011

        System.out.println("a & b  = " + (a & b));   // 1  (0001) 按位与
        System.out.println("a | b  = " + (a | b));   // 7  (0111) 按位或
        System.out.println("a ^ b  = " + (a ^ b));   // 6  (0110) 按位异或
        System.out.println("~a     = " + (~a));      // -6 按位取反
        System.out.println("a << 1 = " + (a << 1));  // 10 左移1位相当于乘2
        System.out.println("a >> 1 = " + (a >> 1));  // 2  右移1位相当于除2

        System.out.println();
    }

    /**
     * 三元运算符: 条件 ? 值1 : 值2
     */
    public static void demonstrateTernary() {
        System.out.println("===== 三元运算符 =====");

        int age = 20;
        String status = (age >= 18) ? "成年人" : "未成年人";
        System.out.println("年龄 " + age + " → " + status);

        int score = 85;
        String grade = (score >= 90) ? "优秀"
                     : (score >= 80) ? "良好"
                     : (score >= 60) ? "及格" : "不及格";
        System.out.println("分数 " + score + " → " + grade);

        System.out.println();
    }
}