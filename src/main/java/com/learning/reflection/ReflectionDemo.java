package com.learning.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 反射(Reflection)
 * <p>
 * 反射允许在运行时动态获取类的信息(类名、方法、字段、构造器)并操作对象。
 * <p>
 * 主要用途:
 * 1. 框架(Spring, MyBatis, JUnit 等核心机制)
 * 2. 动态代理
 * 3. 注解处理
 * 4. 通用工具类
 * <p>
 * 缺点: 性能较低,破坏封装,绕过泛型检查,谨慎使用。
 */
public class ReflectionDemo {

    public static void main(String[] args) throws Exception {
        demonstrateClassObject();
        demonstrateConstructors();
        demonstrateFields();
        demonstrateMethods();
        demonstrateDynamicOperation();
        demonstrateBreakGeneric();
    }

    /**
     * 获取 Class 对象的三种方式
     */
    public static void demonstrateClassObject() {
        System.out.println("===== Class 对象 =====");

        // 方式1: 类名.class
        Class<String> c1 = String.class;

        // 方式2: 对象.getClass()
        String s = "Hello";
        Class<?> c2 = s.getClass();

        // 方式3: Class.forName("全限定类名")
        try {
            Class<?> c3 = Class.forName("java.util.ArrayList");
            System.out.println("Class.forName: " + c3.getName());
        } catch (ClassNotFoundException e) {
            System.out.println("类未找到");
        }

        System.out.println("类名: " + c1.getName());
        System.out.println("简单类名: " + c1.getSimpleName());
        System.out.println("包名: " + c1.getPackageName());
        System.out.println("是否基本类型: " + c1.isPrimitive());
        System.out.println("是否接口: " + c1.isInterface());
        System.out.println("父类: " + c1.getSuperclass().getSimpleName());

        System.out.println();
    }

    /**
     * 构造器操作
     */
    public static void demonstrateConstructors() {
        System.out.println("===== 构造器 =====");

        Class<Student> clazz = Student.class;
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        for (Constructor<?> c : constructors) {
            System.out.println("构造器: " + c);
            System.out.println("  参数数量: " + c.getParameterCount());
        }

        System.out.println();
    }

    /**
     * 字段操作
     */
    public static void demonstrateFields() {
        System.out.println("===== 字段 =====");

        Class<Student> clazz = Student.class;

        // getFields(): 只能获取 public 字段
        // getDeclaredFields(): 获取所有声明的字段(含 private)
        Field[] fields = clazz.getDeclaredFields();

        for (Field f : fields) {
            System.out.println("字段: " + f.getName()
                    + " | 类型: " + f.getType().getSimpleName()
                    + " | 修饰符: " + Modifier.toString(f.getModifiers()));
        }

        System.out.println();
    }

    /**
     * 方法操作
     */
    public static void demonstrateMethods() {
        System.out.println("===== 方法 =====");

        Class<Student> clazz = Student.class;

        // 包含继承的方法
        Method[] methods = clazz.getMethods();

        for (Method m : methods) {
            if (m.getDeclaringClass() == Object.class) continue; // 跳过 Object 方法
            System.out.println("方法: " + m.getName()
                    + " | 返回类型: " + m.getReturnType().getSimpleName()
                    + " | 参数: " + m.getParameterCount());
        }

        System.out.println();
    }

    /**
     * 动态创建对象并调用方法
     */
    public static void demonstrateDynamicOperation() throws Exception {
        System.out.println("===== 动态操作 =====");

        // 动态创建对象
        Class<Student> clazz = Student.class;
        Student student = clazz.getDeclaredConstructor(String.class, int.class)
                .newInstance("张三", 20);
        System.out.println("创建: " + student);

        // 动态调用方法
        Method setName = clazz.getMethod("setName", String.class);
        setName.invoke(student, "李四");
        System.out.println("修改后: " + student);

        // 动态读写 private 字段
        Field nameField = clazz.getDeclaredField("name");
        nameField.setAccessible(true); // 绕过 private 限制(破坏封装)
        nameField.set(student, "王五");
        System.out.println("反射修改 private 后: " + student);

        System.out.println();
    }

    /**
     * 绕过泛型检查(反射的强大但不推荐滥用)
     */
    public static void demonstrateBreakGeneric() throws Exception {
        System.out.println("===== 绕过泛型 =====");

        java.util.ArrayList<Integer> list = new java.util.ArrayList<>();
        list.add(1);

        // 正常情况下,这里不能添加 String
        // list.add("Hello"); // 编译错误

        // 通过反射添加
        java.lang.reflect.Method add = java.util.ArrayList.class.getMethod("add", Object.class);
        add.invoke(list, "Hello");
        add.invoke(list, "World");

        System.out.println("反射添加 String 后: " + list);

        System.out.println();
    }
}

/**
 * 用于演示的实体类
 */
class Student {
    private String name;
    private int age;

    public Student() {}

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public void study() {
        System.out.println(name + " 在学习");
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + "}";
    }
}