package com.learning.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream API(Java 8+)
 * <p>
 * Stream 是一组支持顺序/并行聚合操作的元素序列。
 * <p>
 * 操作分为两类:
 * 1. 中间操作(Intermediate): 返回新 Stream, 链式调用, 惰性求值
 *    filter, map, flatMap, distinct, sorted, limit, skip, peek
 * 2. 终结操作(Terminal): 触发实际计算, 返回非 Stream 结果
 *    forEach, collect, reduce, count, anyMatch, allMatch, noneMatch, findFirst, findAny
 * <p>
 * Stream 不可复用,只能消费一次。
 */
public class StreamDemo {

    public static void main(String[] args) {
        createStreams();
        demonstrateIntermediateOperations();
        demonstrateTerminalOperations();
        demonstrateCollectors();
        demonstrateReduce();
        demonstrateComplexQuery();
        demonstrateParallelStream();
    }

    /**
     * 创建 Stream
     */
    public static void createStreams() {
        System.out.println("===== 创建 Stream =====");

        // 1. 从集合
        List<String> list = List.of("A", "B", "C");
        Stream<String> s1 = list.stream();

        // 2. 从数组
        Stream<Integer> s2 = Arrays.stream(new Integer[]{1, 2, 3});

        // 3. Stream.of
        Stream<String> s3 = Stream.of("X", "Y", "Z");

        // 4. 数值范围(Java 8+)
        IntStream range = IntStream.rangeClosed(1, 5); // 1~5
        System.out.print("IntStream.rangeClosed(1,5): ");
        range.forEach(n -> System.out.print(n + " "));
        System.out.println();

        // 5. 无限流(配合 limit 使用)
        System.out.print("无限流(前5个偶数): ");
        Stream.iterate(0, n -> n + 2).limit(5).forEach(n -> System.out.print(n + " "));
        System.out.println();

        // 6. generate
        System.out.print("generate 随机数(前3个): ");
        Stream.generate(Math::random).limit(3).forEach(n -> System.out.print(String.format("%.3f ", n)));
        System.out.println();

        System.out.println();
    }

    /**
     * 中间操作
     */
    public static void demonstrateIntermediateOperations() {
        System.out.println("===== 中间操作 =====");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // filter: 过滤
        System.out.print("偶数: ");
        numbers.stream().filter(n -> n % 2 == 0).forEach(n -> System.out.print(n + " "));
        System.out.println();

        // map: 转换
        System.out.print("平方: ");
        numbers.stream().map(n -> n * n).forEach(n -> System.out.print(n + " "));
        System.out.println();

        // flatMap: 扁平化
        List<List<Integer>> nested = Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4));
        System.out.print("扁平化: ");
        nested.stream()
              .flatMap(List::stream)
              .forEach(n -> System.out.print(n + " "));
        System.out.println();

        // distinct: 去重
        System.out.print("去重: ");
        Arrays.asList(1, 2, 2, 3, 3, 3).stream().distinct().forEach(n -> System.out.print(n + " "));
        System.out.println();

        // sorted: 排序
        System.out.print("倒序: ");
        numbers.stream().sorted(Comparator.reverseOrder()).limit(3).forEach(n -> System.out.print(n + " "));
        System.out.println();

        // skip + limit: 分页
        System.out.print("skip(2) limit(3): ");
        numbers.stream().skip(2).limit(3).forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.println();
    }

    /**
     * 终结操作
     */
    public static void demonstrateTerminalOperations() {
        System.out.println("===== 终结操作 =====");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // forEach: 遍历
        System.out.print("遍历: ");
        numbers.stream().forEach(n -> System.out.print(n + " "));
        System.out.println();

        // count
        System.out.println("大于3的个数: " + numbers.stream().filter(n -> n > 3).count());

        // anyMatch / allMatch / noneMatch
        System.out.println("存在偶数? " + numbers.stream().anyMatch(n -> n % 2 == 0));
        System.out.println("全部大于0? " + numbers.stream().allMatch(n -> n > 0));
        System.out.println("没有负数? " + numbers.stream().noneMatch(n -> n < 0));

        // findFirst / findAny
        Optional<Integer> first = numbers.stream().filter(n -> n > 3).findFirst();
        System.out.println("第一个大于3的: " + first.orElse(-1));

        // max / min
        System.out.println("最大值: " + numbers.stream().max(Integer::compareTo).orElse(-1));
        System.out.println("最小值: " + numbers.stream().min(Integer::compareTo).orElse(-1));

        System.out.println();
    }

    /**
     * 收集器(Collectors)
     */
    public static void demonstrateCollectors() {
        System.out.println("===== Collectors =====");

        List<Person> people = Arrays.asList(
            new Person("张三", 25, "北京"),
            new Person("李四", 30, "上海"),
            new Person("王五", 25, "北京"),
            new Person("赵六", 30, "深圳")
        );

        // toList: 转 List
        List<String> names = people.stream().map(Person::getName).collect(Collectors.toList());
        System.out.println("姓名列表: " + names);

        // toSet: 转 Set
        java.util.Set<Integer> ages = people.stream().map(Person::getAge).collect(Collectors.toSet());
        System.out.println("年龄集合: " + ages);

        // joining: 拼接字符串
        String joined = people.stream().map(Person::getName).collect(Collectors.joining(", ", "[", "]"));
        System.out.println("姓名拼接: " + joined);

        // groupingBy: 分组
        Map<String, List<Person>> byCity = people.stream()
                .collect(Collectors.groupingBy(Person::getCity));
        System.out.println("按城市分组: " + byCity.keySet());

        // partitioningBy: 二分
        Map<Boolean, List<Person>> partition = people.stream()
                .collect(Collectors.partitioningBy(p -> p.getAge() >= 30));
        System.out.println("年龄>=30的人数: " + partition.get(true).size());

        // summarizingInt: 统计信息
        IntSummaryStatistics stats = people.stream()
                .collect(Collectors.summarizingInt(Person::getAge));
        System.out.println("年龄统计: 平均 = " + stats.getAverage() + ", 最大 = " + stats.getMax());

        System.out.println();
    }

    /**
     * 归约(Reduce)
     */
    public static void demonstrateReduce() {
        System.out.println("===== Reduce =====");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // 求和
        Optional<Integer> sum = numbers.stream().reduce((a, b) -> a + b);
        System.out.println("求和: " + sum.orElse(0));

        // 带初始值的求和
        int sumWithInit = numbers.stream().reduce(10, (a, b) -> a + b);
        System.out.println("带初始值求和: " + sumWithInit);

        // 求最大值
        Optional<Integer> max = numbers.stream().reduce(Integer::max);
        System.out.println("最大值: " + max.orElse(-1));

        System.out.println();
    }

    /**
     * 综合示例: 复杂查询
     */
    public static void demonstrateComplexQuery() {
        System.out.println("===== 综合查询 =====");

        List<Person> people = Arrays.asList(
            new Person("张三", 25, "北京"),
            new Person("李四", 30, "上海"),
            new Person("王五", 25, "北京"),
            new Person("赵六", 30, "深圳"),
            new Person("钱七", 28, "上海")
        );

        // 找出每个城市年龄最大的那个人
        Map<String, Optional<Person>> oldestByCity = people.stream()
                .collect(Collectors.groupingBy(
                        Person::getCity,
                        Collectors.maxBy(Comparator.comparingInt(Person::getAge))
                ));
        System.out.println("各城市年龄最大的人: " + oldestByCity);

        // 按城市分组,统计人数并收集姓名
        Map<String, Long> countByCity = people.stream()
                .collect(Collectors.groupingBy(Person::getCity, Collectors.counting()));
        System.out.println("各城市人数: " + countByCity);

        System.out.println();
    }

    /**
     * 并行流(多线程处理,慎用)
     */
    public static void demonstrateParallelStream() {
        System.out.println("===== ParallelStream =====");

        long start = System.currentTimeMillis();
        long count = IntStream.rangeClosed(1, 10_000_000)
                .parallel()
                .filter(n -> n % 2 == 0)
                .count();
        long end = System.currentTimeMillis();

        System.out.println("并行流: 偶数个数 = " + count + ", 耗时 = " + (end - start) + " ms");

        long start2 = System.currentTimeMillis();
        long count2 = IntStream.rangeClosed(1, 10_000_000)
                .filter(n -> n % 2 == 0)
                .count();
        long end2 = System.currentTimeMillis();

        System.out.println("顺序流: 偶数个数 = " + count2 + ", 耗时 = " + (end2 - start2) + " ms");

        System.out.println();
    }
}