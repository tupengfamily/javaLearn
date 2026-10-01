package com.learning.collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 集合测试
 */
@DisplayName("集合测试")
class CollectionTest {

    @Test
    @DisplayName("List 有序且可重复")
    void testList() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("A"); // 可重复

        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("A", list.get(2));
    }

    @Test
    @DisplayName("Set 不允许重复")
    void testSet() {
        Set<String> set = new HashSet<>();
        set.add("A");
        set.add("B");
        set.add("A"); // 重复,不会添加

        assertEquals(2, set.size());
        assertTrue(set.contains("A"));
        assertTrue(set.contains("B"));
    }

    @Test
    @DisplayName("Map 键值对")
    void testMap() {
        Map<String, Integer> map = new java.util.HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("A", 99); // 覆盖

        assertEquals(2, map.size());
        assertEquals(99, map.get("A"));
    }

    @Test
    @DisplayName("Stream API")
    void testStream() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        long evenCount = numbers.stream()
                .filter(n -> n % 2 == 0)
                .count();

        assertEquals(5, evenCount);

        int sum = numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();

        assertEquals(55, sum);

        List<Integer> doubled = numbers.stream()
                .map(n -> n * 2)
                .collect(Collectors.toList());

        assertEquals(20, doubled.get(9)); // 10 * 2
    }
}