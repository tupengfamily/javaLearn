package com.learning.enumannotation;

/**
 * 枚举使用演示
 */
public class EnumDemo {

    public static void main(String[] args) {
        demonstrateBasic();
        demonstrateWithFields();
        demonstrateSwitch();
        demonstrateMethods();
    }

    /**
     * 枚举的基本使用
     */
    public static void demonstrateBasic() {
        System.out.println("===== 基本枚举 =====");

        // 单个枚举
        Status status = Status.PENDING;
        System.out.println("当前状态: " + status);

        // values(): 返回所有枚举值的数组
        System.out.println("所有状态:");
        for (Status s : Status.values()) {
            System.out.println("  - " + s.name() + ": " + s.ordinal());
            // ordinal() 返回枚举在声明时的下标(从 0 开始)
        }

        // valueOf(): 根据名称获取枚举
        Status completed = Status.valueOf("COMPLETED");
        System.out.println("按名称查找: " + completed);

        System.out.println();
    }

    /**
     * 带字段和方法的枚举
     */
    public static void demonstrateWithFields() {
        System.out.println("===== 带字段的枚举 =====");

        for (Status s : Status.values()) {
            System.out.println(s + " → code=" + s.getCode());
        }

        // 用静态方法查找
        Status found = Status.fromCode(2);
        System.out.println("code=2 对应: " + found);

        System.out.println();
    }

    /**
     * 枚举与 switch
     */
    public static void demonstrateSwitch() {
        System.out.println("===== 枚举与 switch =====");

        Status current = Status.PROCESSING;
        String message = switch (current) {
            case PENDING -> "订单待处理";
            case PROCESSING -> "订单处理中,请耐心等待";
            case COMPLETED -> "订单已完成";
            case FAILED -> "订单处理失败,请重试";
        };
        System.out.println(current + " → " + message);

        System.out.println();
    }

    /**
     * 枚举常用方法
     */
    public static void demonstrateMethods() {
        System.out.println("===== 枚举方法 =====");

        Status status = Status.COMPLETED;

        System.out.println("名称: " + status.name());
        System.out.println("下标: " + status.ordinal());
        System.out.println("Class: " + status.getDeclaringClass().getName());

        // 比较
        System.out.println("与 FAILED 比较: " + status.compareTo(Status.FAILED));
        System.out.println("等于 COMPLETED: " + status.equals(Status.COMPLETED));

        System.out.println();
    }
}