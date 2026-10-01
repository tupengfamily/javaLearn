package com.learning.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * <p>
 * 关键注解:
 * - @Entity: 标识这是一个 JPA 实体,会被映射到数据库表
 * - @Table: 指定对应的表名
 * - @Id: 主键
 * - @GeneratedValue: 主键生成策略
 * - @Column: 字段约束(非空、长度等)
 */
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private Integer age;

    @Column(length = 200)
    private String address;

    /**
     * 创建时间,由 JPA 自动填充
     */
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public User() {}

    public User(String username, String email, Integer age, String address) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.address = address;
    }

    /**
     * JPA 回调:持久化前自动调用
     */
    @jakarta.persistence.PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now();
        this.updateTime = this.createTime;
    }

    /**
     * JPA 回调:更新前自动调用
     */
    @jakarta.persistence.PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }

    // ============== Getter / Setter ==============

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', email='" + email
                + "', age=" + age + ", address='" + address + "'}";
    }
}