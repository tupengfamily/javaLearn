package com.learning.collection;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Set 集合
 * <p>
 * 特点: 元素不可重复
 * <p>
 * 主要实现类:
 * - HashSet: 基于哈希表,无序,查找 O(1)
 * - LinkedHashSet: HashSet 的子类,保持插入顺序
 * - TreeSet: 基于红黑树,有序(自然顺序或自定义),查找 O(log n)
 */
public class SetDemo {

    public static void main(String[] args) {
        demonstrateHashSet();
        demonstrateLinkedHashSet();
        demonstrateTreeSet();
    }

    /**
     * HashSet
     */
    public static void demonstrateHashSet() {
        System.out.println("===== HashSet =====");

        Set<String> set = new HashSet<>();

        // 添加元素
        set.add("苹果");
        set.add("香蕉");
        set.add("橙子");
        set.add("苹果"); // 重复元素会被忽略
        set.add(null);   // 允许一个 null

        System.out.println("HashSet: " + set);
        System.out.println("大小: " + set.size());

        System.out.println("是否包含苹果: " + set.contains("苹果"));

        // 删除
        set.remove("苹果");
        System.out.println("删除后: " + set);

        System.out.println();
    }

    /**
     * LinkedHashSet: 保持插入顺序
     */
    public static void demonstrateLinkedHashSet() {
        System.out.println("===== LinkedHashSet =====");

        Set<String> set = new LinkedHashSet<>();
        set.add("B");
        set.add("A");
        set.add("C");
        set.add("B");

        System.out.println("LinkedHashSet (保持插入顺序): " + set);

        System.out.println();
    }

    /**
     * TreeSet: 自然排序或自定义排序
     */
    public static void demonstrateTreeSet() {
        System.out.println("===== TreeSet =====");

        // 自然排序
        Set<Integer> treeSet = new TreeSet<>();
        treeSet.add(5);
        treeSet.add(1);
        treeSet.add(3);
        treeSet.add(2);
        treeSet.add(4);
        System.out.println("TreeSet 自然排序: " + treeSet);

        // 自定义排序: 倒序
        Set<Integer> reversedSet = new TreeSet<>(Comparator.reverseOrder());
        reversedSet.addAll(treeSet);
        System.out.println("TreeSet 倒序: " + reversedSet);

        // TreeSet 自定义比较: 字符串按长度排
        TreeSet<String> byLength = new TreeSet<>(Comparator.comparingInt(String::length));
        byLength.add("Apple");
        byLength.add("Hi");
        byLength.add("Banana");
        System.out.println("按长度排: " + byLength);

        System.out.println();
    }
}