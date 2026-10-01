package com.learning.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * List 集合
 * <p>
 * 特点:
 * 1. 有序(插入顺序)
 * 2. 可重复
 * 3. 可通过下标访问
 * <p>
 * 主要实现类:
 * - ArrayList: 数组实现,查询快,增删慢
 * - LinkedList: 链表实现,增删快,查询慢
 * - Vector: 古老的实现,线程安全(已不推荐)
 */
public class ListDemo {

    public static void main(String[] args) {
        demonstrateArrayList();
        demonstrateLinkedList();
        demonstrateCommonOperations();
        demonstrateIteration();
    }

    /**
     * ArrayList
     */
    public static void demonstrateArrayList() {
        System.out.println("===== ArrayList =====");

        // 创建
        List<String> list = new ArrayList<>();

        // 添加元素
        list.add("苹果");
        list.add("香蕉");
        list.add("橙子");
        list.add("苹果"); // 可以重复

        System.out.println("初始: " + list);

        // 指定位置插入
        list.add(1, "葡萄");
        System.out.println("插入后: " + list);

        // 访问
        System.out.println("下标0的元素: " + list.get(0));
        System.out.println("大小: " + list.size());
        System.out.println("是否包含苹果: " + list.contains("苹果"));

        // 修改
        list.set(0, "芒果");
        System.out.println("修改后: " + list);

        // 删除
        list.remove("苹果"); // 删除第一个匹配项
        list.remove(0);      // 按下标删除
        System.out.println("删除后: " + list);

        System.out.println();
    }

    /**
     * LinkedList
     */
    public static void demonstrateLinkedList() {
        System.out.println("===== LinkedList =====");

        LinkedList<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");
        list.addLast("C"); // 末尾添加
        list.addFirst("D"); // 开头添加

        System.out.println("LinkedList: " + list);

        System.out.println("第一个: " + list.getFirst());
        System.out.println("最后一个: " + list.getLast());
        System.out.println("弹出第一个: " + list.pollFirst());
        System.out.println("弹出后: " + list);

        // 用作栈
        list.push("E");
        System.out.println("压栈后: " + list);
        System.out.println("弹栈: " + list.pop());

        // 用作队列
        list.offer("F"); // 入队
        System.out.println("入队后: " + list);
        System.out.println("出队: " + list.poll());

        System.out.println();
    }

    /**
     * 常用操作
     */
    public static void demonstrateCommonOperations() {
        System.out.println("===== 常用操作 =====");

        List<Integer> numbers = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6));

        System.out.println("原始: " + numbers);

        // 排序
        numbers.sort(Integer::compareTo); // 自然排序
        System.out.println("排序后: " + numbers);

        // 反转
        java.util.Collections.reverse(numbers);
        System.out.println("反转后: " + numbers);

        // 子列表视图(原列表变化会反映到子列表)
        List<Integer> sub = numbers.subList(0, 3);
        System.out.println("子列表[0,3): " + sub);

        // 数组与集合互转
        Integer[] arr = numbers.toArray(new Integer[0]);
        System.out.println("转数组: " + Arrays.toString(arr));

        List<Integer> list2 = Arrays.asList(arr); // 固定大小列表
        List<Integer> list3 = new ArrayList<>(Arrays.asList(arr)); // 可变列表

        // 不可变集合(Java 9+)
        List<String> immutable = List.of("A", "B", "C");
        System.out.println("不可变集合: " + immutable);
        try {
            immutable.add("D");
        } catch (UnsupportedOperationException e) {
            System.out.println("不可变集合不能修改");
        }

        System.out.println();
    }

    /**
     * 四种遍历方式
     */
    public static void demonstrateIteration() {
        System.out.println("===== 遍历方式 =====");

        List<String> list = new ArrayList<>(List.of("A", "B", "C", "D"));

        // 方式1: 普通 for 循环(可以修改)
        System.out.println("--- 普通 for ---");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        // 方式2: 增强 for 循环(只能读,不能增删)
        System.out.println("--- foreach ---");
        for (String s : list) {
            System.out.println(s);
        }

        // 方式3: 迭代器(可以安全删除)
        System.out.println("--- Iterator ---");
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String s = it.next();
            System.out.println(s);
            // 边遍历边删除要用 iterator.remove()
        }

        // 方式4: forEach + Lambda(Java 8+)
        System.out.println("--- forEach + Lambda ---");
        list.forEach(System.out::println);

        System.out.println();
    }
}