package com.learning.project.service;

import com.learning.project.model.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 学生管理服务
 * <p>
 * 业务逻辑层,封装对学生数据的各种操作。
 * 综合演示:
 * - 集合的增删改查
 * - Stream API 的实际应用
 * - 异常处理
 * - 自定义业务异常
 */
public class StudentService {

    private final List<Student> students = new ArrayList<>();

    /**
     * 添加学生(学号重复时抛异常)
     */
    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("学生对象不能为 null");
        }
        if (findById(student.getId()).isPresent()) {
            throw new RuntimeException("学号已存在: " + student.getId());
        }
        students.add(student);
        System.out.println("✓ 已添加: " + student);
    }

    /**
     * 根据学号查找
     */
    public Optional<Student> findById(String id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    /**
     * 根据姓名模糊查询
     */
    public List<Student> findByName(String name) {
        return students.stream()
                .filter(s -> s.getName().contains(name))
                .collect(Collectors.toList());
    }

    /**
     * 删除学生
     */
    public boolean removeStudent(String id) {
        Optional<Student> student = findById(id);
        if (student.isPresent()) {
            students.remove(student.get());
            System.out.println("✓ 已删除: " + student.get());
            return true;
        }
        System.out.println("✗ 学号不存在: " + id);
        return false;
    }

    /**
     * 修改学生信息
     */
    public boolean updateStudent(String id, String name, int age, double score) {
        Optional<Student> optional = findById(id);
        if (optional.isEmpty()) {
            System.out.println("✗ 学号不存在: " + id);
            return false;
        }
        Student student = optional.get();
        student.setName(name);
        student.setAge(age);
        student.setScore(score);
        System.out.println("✓ 已更新: " + student);
        return true;
    }

    /**
     * 获取所有学生
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(students); // 返回副本,避免外部修改
    }

    /**
     * 按分数排序(降序)
     */
    public List<Student> sortByScore() {
        return students.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * 按分数排序(升序)
     */
    public List<Student> sortByScoreAsc() {
        return students.stream()
                .sorted(Comparator.comparingDouble(Student::getScore))
                .collect(Collectors.toList());
    }

    /**
     * 统计及格人数
     */
    public long countPassed() {
        return students.stream().filter(Student::isPassed).count();
    }

    /**
     * 计算平均分
     */
    public double averageScore() {
        return students.stream()
                .mapToDouble(Student::getScore)
                .average()
                .orElse(0.0);
    }

    /**
     * 获取最高分学生
     */
    public Optional<Student> getTopStudent() {
        return students.stream().max(Comparator.comparingDouble(Student::getScore));
    }

    /**
     * 获取最低分学生
     */
    public Optional<Student> getLowestStudent() {
        return students.stream().min(Comparator.comparingDouble(Student::getScore));
    }

    /**
     * 按分数段分组
     */
    public Map<String, Long> groupByScoreLevel() {
        return students.stream()
                .collect(Collectors.groupingBy(
                        s -> {
                            double score = s.getScore();
                            if (score >= 90) return "优秀(90+)";
                            if (score >= 80) return "良好(80-89)";
                            if (score >= 60) return "及格(60-79)";
                            return "不及格(<60)";
                        },
                        Collectors.counting()
                ));
    }

    /**
     * 统计总人数
     */
    public int count() {
        return students.size();
    }

    /**
     * 打印所有学生
     */
    public void printAll() {
        System.out.println("--- 学生列表 (共 " + students.size() + " 人) ---");
        students.forEach(System.out::println);
        System.out.println("--- end ---\n");
    }
}