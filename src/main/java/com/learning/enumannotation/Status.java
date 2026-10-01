package com.learning.enumannotation;

/**
 * 枚举(enum)
 * <p>
 * 枚举是一组固定常量的集合,本质是一个继承 java.lang.Enum 的类。
 * <p>
 * 用法:
 * 1. 基本枚举
 * 2. 带字段和方法的枚举(更强大的枚举)
 * 3. 用于 switch
 */
public enum Status {
    // 枚举常量,每个都是 Status 的实例
    PENDING("待处理", 1),
    PROCESSING("处理中", 2),
    COMPLETED("已完成", 3),
    FAILED("已失败", 4);

    private final String description; // 描述
    private final int code;           // 编码

    // 枚举构造方法默认 private
    Status(String description, int code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    /**
     * 静态方法: 根据 code 查找枚举
     */
    public static Status fromCode(int code) {
        for (Status s : values()) {
            if (s.code == code) {
                return s;
            }
        }
        throw new IllegalArgumentException("未知的 code: " + code);
    }

    @Override
    public String toString() {
        return name() + "(" + description + ")";
    }
}