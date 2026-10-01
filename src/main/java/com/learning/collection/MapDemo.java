package com.learning.collection;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Map 集合
 * <p>
 * 特点: 键值对(Key-Value),Key 不能重复
 * <p>
 * 主要实现类:
 * - HashMap: 基于哈希表,无序,允许 null 键和值
 * - LinkedHashMap: 保持插入顺序
 * - TreeMap: 基于红黑树,Key 有序
 * - Hashtable: 古老的实现,线程安全,不允许 null(已不推荐)
 */
public class MapDemo {

    public static void main(String[] args) {
        demonstrateHashMap();
        demonstrateLinkedHashMap();
        demonstrateTreeMap();
        demonstrateIteration();
        demonstrateUsefulMethods();
    }

    /**
     * HashMap
     */
    public static void demonstrateHashMap() {
        System.out.println("===== HashMap =====");

        Map<String, Integer> map = new HashMap<>();
        map.put("苹果", 5);
        map.put("香蕉", 3);
        map.put("橙子", 8);
        map.put("苹果", 10); // 相同 Key 会覆盖值

        System.out.println("HashMap: " + map);

        // 获取
        System.out.println("苹果的数量: " + map.get("苹果"));
        System.out.println("不存在的Key: " + map.get("葡萄")); // null

        // 安全的获取(带默认值)
        Integer count = map.getOrDefault("葡萄", 0);
        System.out.println("葡萄的数量(默认0): " + count);

        // 判断
        System.out.println("是否包含Key苹果: " + map.containsKey("苹果"));
        System.out.println("是否包含Value 10: " + map.containsValue(10));

        // 删除
        map.remove("香蕉");
        System.out.println("删除后: " + map);

        System.out.println();
    }

    /**
     * LinkedHashMap: 保持插入顺序
     */
    public static void demonstrateLinkedHashMap() {
        System.out.println("===== LinkedHashMap =====");

        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("B", 2);
        map.put("A", 1);
        map.put("C", 3);

        System.out.println("LinkedHashMap (插入顺序): " + map);

        System.out.println();
    }

    /**
     * TreeMap: Key 自然排序
     */
    public static void demonstrateTreeMap() {
        System.out.println("===== TreeMap =====");

        Map<String, Integer> map = new TreeMap<>();
        map.put("Banana", 2);
        map.put("Apple", 1);
        map.put("Cherry", 3);

        System.out.println("TreeMap (字母顺序): " + map);

        System.out.println();
    }

    /**
     * Map 遍历
     */
    public static void demonstrateIteration() {
        System.out.println("===== Map 遍历 =====");

        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);

        // 方式1: 遍历 entrySet(最常用)
        System.out.println("--- entrySet ---");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }

        // 方式2: 遍历 keySet
        System.out.println("--- keySet ---");
        for (String key : map.keySet()) {
            System.out.println(key + " → " + map.get(key));
        }

        // 方式3: 遍历 values
        System.out.println("--- values ---");
        for (Integer value : map.values()) {
            System.out.println("值: " + value);
        }

        // 方式4: forEach + Lambda
        System.out.println("--- forEach ---");
        map.forEach((k, v) -> System.out.println(k + " = " + v));

        System.out.println();
    }

    /**
     * 其他常用方法
     */
    public static void demonstrateUsefulMethods() {
        System.out.println("===== 实用方法 =====");

        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);

        // putIfAbsent: 不存在才放入
        map.putIfAbsent("A", 99); // 已存在,不修改
        map.putIfAbsent("C", 3);  // 不存在,放入
        System.out.println("putIfAbsent 后: " + map);

        // replace: 替换值(仅当 Key 存在时)
        map.replace("A", 100);
        System.out.println("replace 后: " + map);

        // computeIfAbsent: Key 不存在时计算并放入
        map.computeIfAbsent("D", k -> k.length() * 10);
        System.out.println("computeIfAbsent 后: " + map);

        // merge: 合并
        Map<String, Integer> other = Map.of("A", 50, "E", 5);
        other.forEach((k, v) -> map.merge(k, v, Integer::sum));
        System.out.println("merge 后: " + map);

        System.out.println();
    }
}