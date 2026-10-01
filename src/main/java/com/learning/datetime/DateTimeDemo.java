package com.learning.datetime;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * 日期时间 API(Java 8+)
 * <p>
 * 旧的 API(java.util.Date, Calendar)存在线程不安全、设计缺陷等问题。
 * Java 8 引入了 java.time 包,基于 Joda-Time 设计,线程安全,API 清晰。
 * <p>
 * 核心类:
 * - LocalDate: 日期(年月日)
 * - LocalTime: 时间(时分秒)
 * - LocalDateTime: 日期 + 时间
 * - Instant: 时间戳(秒)
 * - Duration: 时间间隔(秒、纳秒)
 * - Period: 日期间隔(年、月、日)
 * - ZonedDateTime: 带时区的日期时间
 */
public class DateTimeDemo {

    public static void main(String[] args) {
        demonstrateLocalDate();
        demonstrateLocalTime();
        demonstrateLocalDateTime();
        demonstrateInstant();
        demonstrateDurationAndPeriod();
        demonstrateFormatting();
        demonstrateZonedDateTime();
    }

    /**
     * LocalDate: 只有日期
     */
    public static void demonstrateLocalDate() {
        System.out.println("===== LocalDate =====");

        LocalDate today = LocalDate.now();           // 今天
        LocalDate specific = LocalDate.of(2026, 1, 1); // 指定日期
        LocalDate parsed = LocalDate.parse("2026-10-01"); // 解析

        System.out.println("今天: " + today);
        System.out.println("指定日期: " + specific);
        System.out.println("解析: " + parsed);

        // 获取字段
        System.out.println("年: " + today.getYear());
        System.out.println("月: " + today.getMonthValue() + " (" + today.getMonth() + ")");
        System.out.println("日: " + today.getDayOfMonth());
        System.out.println("星期: " + today.getDayOfWeek() + " (" + DayOfWeek.of(today.getDayOfWeek().getValue()) + ")");
        System.out.println("是否闰年: " + today.isLeapYear());
        System.out.println("本年第几天: " + today.getDayOfYear());

        // 计算
        LocalDate tomorrow = today.plusDays(1);
        LocalDate nextMonth = today.plusMonths(1);
        LocalDate lastYear = today.minusYears(1);
        System.out.println("明天: " + tomorrow);
        System.out.println("下月: " + nextMonth);
        System.out.println("去年: " + lastYear);

        // 比较
        System.out.println("今天在指定日期前? " + today.isBefore(specific));
        System.out.println("今天在指定日期后? " + today.isAfter(specific));

        // 调整器: TemporalAdjusters
        LocalDate firstDayOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate lastDayOfYear = today.with(TemporalAdjusters.lastDayOfYear());
        System.out.println("本月第一天: " + firstDayOfMonth);
        System.out.println("下周一: " + nextMonday);
        System.out.println("本年最后一天: " + lastDayOfYear);

        System.out.println();
    }

    /**
     * LocalTime: 只有时间
     */
    public static void demonstrateLocalTime() {
        System.out.println("===== LocalTime =====");

        LocalTime now = LocalTime.now();
        LocalTime specific = LocalTime.of(14, 30, 0);

        System.out.println("现在: " + now);
        System.out.println("指定时间: " + specific);

        System.out.println("小时: " + now.getHour());
        System.out.println("分钟: " + now.getMinute());
        System.out.println("秒: " + now.getSecond());
        System.out.println("纳秒: " + now.getNano());

        System.out.println();
    }

    /**
     * LocalDateTime: 日期 + 时间
     */
    public static void demonstrateLocalDateTime() {
        System.out.println("===== LocalDateTime =====");

        LocalDateTime now = LocalDateTime.now();
        System.out.println("现在: " + now);

        LocalDateTime custom = LocalDateTime.of(2026, Month.JANUARY, 1, 12, 0, 0);
        System.out.println("自定义: " + custom);

        // 与 LocalDate / LocalTime 互转
        LocalDate date = now.toLocalDate();
        LocalTime time = now.toLocalTime();
        System.out.println("日期部分: " + date);
        System.out.println("时间部分: " + time);

        System.out.println();
    }

    /**
     * Instant: 时间戳,常用于时间间隔计算
     */
    public static void demonstrateInstant() {
        System.out.println("===== Instant =====");

        Instant now = Instant.now();
        Instant later = now.plusSeconds(60);

        System.out.println("当前时间戳(秒): " + now.getEpochSecond());
        System.out.println("当前时间戳(毫秒): " + now.toEpochMilli());

        // 时间间隔
        Duration duration = Duration.between(now, later);
        System.out.println("相差秒: " + duration.getSeconds());

        System.out.println();
    }

    /**
     * Duration 和 Period
     * <p>
     * - Duration: 秒、纳秒级时间差(LocalDateTime, Instant)
     * - Period: 年月日级日期间隔(LocalDate)
     */
    public static void demonstrateDurationAndPeriod() {
        System.out.println("===== Duration 与 Period =====");

        LocalDate birth = LocalDate.of(2000, 1, 1);
        LocalDate today = LocalDate.now();

        Period age = Period.between(birth, today);
        System.out.println("年龄: " + age.getYears() + " 年 " + age.getMonths() + " 月 " + age.getDays() + " 天");

        // Duration 用于时间
        LocalDateTime start = LocalDateTime.of(2026, 1, 1, 9, 0);
        LocalDateTime end = LocalDateTime.of(2026, 1, 1, 18, 30);
        Duration workDuration = Duration.between(start, end);
        System.out.println("工作时长: " + workDuration.toHours() + " 小时");

        // 简化的天数计算
        long days = ChronoUnit.DAYS.between(birth, today);
        System.out.println("活了 " + days + " 天");

        System.out.println();
    }

    /**
     * 格式化与解析
     */
    public static void demonstrateFormatting() {
        System.out.println("===== 格式化 =====");

        LocalDateTime now = LocalDateTime.now();

        // 内置格式
        System.out.println("ISO: " + now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        // 自定义格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String formatted = now.format(formatter);
        System.out.println("自定义: " + formatted);

        // 解析
        LocalDate parsed = LocalDate.parse("2026年10月01日",
                DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
        System.out.println("解析: " + parsed);

        System.out.println();
    }

    /**
     * 时区
     */
    public static void demonstrateZonedDateTime() {
        System.out.println("===== 时区 =====");

        // 获取所有时区(太多,演示几个)
        ZoneId shanghai = ZoneId.of("Asia/Shanghai");
        ZoneId newYork = ZoneId.of("America/New_York");
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");

        ZonedDateTime nowInShanghai = ZonedDateTime.now(shanghai);
        System.out.println("上海现在: " + nowInShanghai);

        // 时区转换
        ZonedDateTime inNewYork = nowInShanghai.withZoneSameInstant(newYork);
        System.out.println("纽约: " + inNewYork);

        ZonedDateTime inTokyo = nowInShanghai.withZoneSameInstant(tokyo);
        System.out.println("东京: " + inTokyo);

        // 系统默认时区
        System.out.println("默认时区: " + ZoneId.systemDefault());

        System.out.println();
    }
}