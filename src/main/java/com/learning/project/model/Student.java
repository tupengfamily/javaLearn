package com.learning.project.model;

import java.util.Objects;

/**
 * 学生实体类
 * <p>
 * 综合运用:
 * - 封装(private + getter/setter)
 * - equals & hashCode 重写(便于存入 Set/Map)
 * - toString 重写
 * - Comparable 接口(便于排序)
 */
public class Student implements Comparable<Student> {

    private String id;       // 学号
    private String name;     // 姓名
    private int age;         // 年龄
    private double score;    // 分数

    public Student() {}

    public Student(String id, String name, int age, double score) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    /**
     * 判断是否及格
     */
    public boolean isPassed() {
        return score >= 60;
    }

    /**
     * 重写 equals: 用于比较两个学生是否相等(按学号)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Student{id='%s', name='%s', age=%d, score=%.1f, passed=%s}",
                id, name, age, score, isPassed() ? "✓" : "✗");
    }

    /**
     * 自然排序: 按分数降序
     */
    @Override
    public int compareTo(Student other) {
        return Double.compare(other.score, this.score);
    }
}