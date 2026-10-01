package com.learning.project;

import com.learning.project.model.Student;
import com.learning.project.service.StudentService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 学生管理系统 - 主程序
 * <p>
 * 综合实战项目,演示完整业务流程。
 * <p>
 * 学习要点回顾:
 * - OOP: 封装、继承、多态
 * - 集合: ArrayList, Stream API
 * - 异常: try-catch
 * - Lambda: 函数式编程
 * - 枚举: 业务状态
 */
public class Main {

    public static void main(String[] args) {
        // 创建服务实例
        StudentService service = new StudentService();

        // 1. 添加学生
        System.out.println("===== 1. 添加学生 =====");
        service.addStudent(new Student("S001", "张三", 20, 95.5));
        service.addStudent(new Student("S002", "李四", 21, 88.0));
        service.addStudent(new Student("S003", "王五", 19, 72.5));
        service.addStudent(new Student("S004", "赵六", 20, 56.0));
        service.addStudent(new Student("S005", "钱七", 22, 91.5));
        service.addStudent(new Student("S006", "孙八", 21, 84.5));

        // 2. 尝试添加重复学号(异常处理演示)
        try {
            service.addStudent(new Student("S001", "重复", 20, 5));
        } catch (RuntimeException e) {
            System.out.println("✗ 异常: " + e.getMessage());
        }

        // 3. 查询
        System.out.println("\n===== 2. 查询学生 =====");
        Optional<Student> found = service.findById("S003");
        found.ifPresent(s -> System.out.println("找到: " + s));

        List<Student> wangStudents = service.findByName("王");
        System.out.println("包含'王'的学生: " + wangStudents);

        // 4. 修改
        System.out.println("\n===== 3. 修改学生 =====");
        service.updateStudent("S003", "王五五", 20, 80.0);

        // 5. 排序
        System.out.println("\n===== 4. 按分数排序 =====");
        service.sortByScore().forEach(System.out::println);

        // 6. 统计信息
        System.out.println("\n===== 5. 统计信息 =====");
        System.out.println("总人数: " + service.count());
        System.out.println("及格人数: " + service.countPassed());
        System.out.printf("平均分: %.2f%n", service.averageScore());

        Optional<Student> top = service.getTopStudent();
        top.ifPresent(s -> System.out.println("最高分: " + s));

        Optional<Student> lowest = service.getLowestStudent();
        lowest.ifPresent(s -> System.out.println("最低分: " + s));

        // 7. 分组统计
        System.out.println("\n===== 6. 按分数段分组 =====");
        Map<String, Long> groups = service.groupByScoreLevel();
        groups.forEach((level, count) ->
                System.out.println("  " + level + ": " + count + " 人"));

        // 8. 删除学生
        System.out.println("\n===== 7. 删除学生 =====");
        service.removeStudent("S006");
        service.removeStudent("S999"); // 不存在的

        // 9. 打印最终列表
        System.out.println();
        service.printAll();

        System.out.println("✓ 程序结束");
    }
}