package com.learning.enumannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * 注解(Annotation)
 * <p>
 * 注解本质是元数据,用于为代码添加信息,不影响程序逻辑。
 * <p>
 * 内置注解:
 * - @Override: 标记重写父类方法
 * - @Deprecated: 标记已过时
 * - @SuppressWarnings: 抑制编译器警告
 * - @FunctionalInterface: 标记函数式接口
 * <p>
 * 元注解(修饰注解的注解):
 * - @Target: 注解可以应用的位置
 * - @Retention: 注解的保留策略(SOURCE/CLASS/RUNTIME)
 * - @Documented: 是否包含在 Javadoc 中
 * - @Inherited: 是否被子类继承
 */
public class AnnotationDemo {

    public static void main(String[] args) throws Exception {
        demonstrateBuiltIn();
        demonstrateCustomAnnotation();
        demonstrateRuntimeAnnotation();
    }

    /**
     * 内置注解
     */
    @Deprecated(since = "1.5", forRemoval = false)
    public static void oldMethod() {
        System.out.println("这是一个过时的方法");
    }

    /**
     * @SuppressWarnings 抑制警告
     */
    @SuppressWarnings("unused")
    public static void demonstrateBuiltIn() {
        System.out.println("===== 内置注解 =====");

        // @Override 示例
        // @Override public String toString() { return ""; } // 实际父类没有此方法会编译错误

        // @Deprecated 示例
        oldMethod(); // 编译器会有删除线提示

        // @SuppressWarnings 示例
        @SuppressWarnings("all") // 抑制所有警告
        class NoWarnings {
            int unused;
        }

        System.out.println();
    }

    /**
     * 自定义注解
     */
    public static void demonstrateCustomAnnotation() {
        System.out.println("===== 自定义注解 =====");

        // 通过反射读取注解信息
        Class<User> clazz = User.class;
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            System.out.println("User 对应的表名: " + table.value());
        }

        try {
            Field nameField = User.class.getDeclaredField("name");
            if (nameField.isAnnotationPresent(Column.class)) {
                Column column = nameField.getAnnotation(Column.class);
                System.out.println("name 字段对应列: " + column.value()
                        + ", 长度=" + column.length()
                        + ", 是否可空=" + column.nullable());
            }
        } catch (NoSuchFieldException e) {
            System.out.println("字段不存在");
        }

        System.out.println();
    }

    /**
     * 运行时注解: 通过反射动态处理
     */
    public static void demonstrateRuntimeAnnotation() throws Exception {
        System.out.println("===== 运行时注解 =====");

        // 模拟 ORM: 根据注解把对象转 SQL
        User user = new User("张三", 25);
        String sql = generateInsertSql(user);
        System.out.println("生成的 SQL: " + sql);

        System.out.println();
    }

    /**
     * 简易 ORM: 根据 @Table 和 @Column 生成 SQL
     */
    public static String generateInsertSql(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();
        Table table = clazz.getAnnotation(Table.class);
        String tableName = (table != null) ? table.value() : clazz.getSimpleName();

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Field field : clazz.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column == null) continue;

            if (columns.length() > 0) columns.append(", ");
            columns.append(column.value());

            field.setAccessible(true);
            Object value = field.get(obj);

            if (values.length() > 0) values.append(", ");
            values.append(value instanceof String ? "'" + value + "'" : value);
        }

        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values);
    }
}

/**
 * 自定义注解: @Table
 * <p>
 * - RetentionPolicy.RUNTIME: 运行时可用(可通过反射读取)
 * - Target(ElementType.TYPE): 只能用在类/接口/枚举上
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Table {
    String value();
}

/**
 * 自定义注解: @Column
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column {
    String value();
    int length() default 255;
    boolean nullable() default true;
}

/**
 * 使用自定义注解的实体类
 */
@Table("t_user")
class User {
    @Column(value = "user_name", length = 50, nullable = false)
    private String name;

    @Column(value = "user_age")
    private int age;

    public User() {}

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
}