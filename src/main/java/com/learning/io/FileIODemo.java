package com.learning.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Java I/O
 * <p>
 * 传统 I/O(java.io): 流(Stream)方式
 * 新 I/O(java.nio): 通道(Channel)和缓冲区(Buffer),更高效
 * <p>
 * 本例使用 NIO 的 Files 和 Paths 工具类,代码更简洁
 */
public class FileIODemo {

    public static void main(String[] args) {
        demonstrateWriteFile();
        demonstrateReadFile();
        demonstrateFileOperations();
        demonstrateStreamCopy();
    }

    /**
     * 写入文件
     */
    public static void demonstrateWriteFile() {
        System.out.println("===== 写入文件 =====");

        Path path = Paths.get("demo-output.txt");
        String content = """
                这是第一行
                这是第二行
                Java 21 学习笔记
                """;

        try {
            // 写入(默认覆盖)
            Files.writeString(path, content);
            System.out.println("写入成功: " + path);

            // 追加
            Files.writeString(path, "\n追加的内容", StandardOpenOption.APPEND);
            System.out.println("追加成功");
        } catch (IOException e) {
            System.out.println("写入失败: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * 读取文件
     */
    public static void demonstrateReadFile() {
        System.out.println("===== 读取文件 =====");

        Path path = Paths.get("demo-output.txt");

        try {
            // 一次性读为字符串
            String content = Files.readString(path);
            System.out.println("--- 文件内容 ---");
            System.out.println(content);
            System.out.println("--- end ---");

            // 按行读取
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            System.out.println("总行数: " + lines.size());
            System.out.println("第一行: " + lines.get(0));
        } catch (IOException e) {
            System.out.println("读取失败: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * 文件和目录操作
     */
    public static void demonstrateFileOperations() {
        System.out.println("===== 文件操作 =====");

        try {
            // 创建目录
            Path dir = Paths.get("demo-dir");
            Files.createDirectories(dir);
            System.out.println("创建目录: " + dir);

            // 创建文件
            Path file = dir.resolve("test.txt");
            Files.writeString(file, "测试文件内容");
            System.out.println("创建文件: " + file);

            // 判断文件属性
            System.out.println("存在: " + Files.exists(file));
            System.out.println("是文件: " + Files.isRegularFile(file));
            System.out.println("是目录: " + Files.isDirectory(file));
            System.out.println("大小: " + Files.size(file) + " 字节");

            // 复制
            Path copy = Paths.get("demo-copy.txt");
            Files.copy(file, copy);
            System.out.println("复制到: " + copy);

            // 移动/重命名
            Path moved = Paths.get("demo-moved.txt");
            Files.move(copy, moved, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            System.out.println("移动到: " + moved);

            // 删除文件
            Files.delete(file);
            Files.delete(moved);
            System.out.println("删除文件");

            // 删除目录(必须为空)
            Files.delete(dir);
            System.out.println("删除目录");

            // 清理 demo-output.txt
            Files.deleteIfExists(Paths.get("demo-output.txt"));
        } catch (IOException e) {
            System.out.println("文件操作失败: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * 流式拷贝(适合大文件)
     */
    public static void demonstrateStreamCopy() {
        System.out.println("===== 流式拷贝 =====");

        Path src = Paths.get("pom.xml");
        Path dst = Paths.get("pom-copy.xml");

        try {
            // Files.copy(InputStream, target, options) 适合大文件
            try (var in = Files.newInputStream(src)) {
                Files.copy(in, dst, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
            System.out.println("流式拷贝成功: " + dst);

            // 清理
            Files.deleteIfExists(dst);
        } catch (IOException e) {
            System.out.println("流式拷贝失败: " + e.getMessage());
        }

        System.out.println();
    }
}