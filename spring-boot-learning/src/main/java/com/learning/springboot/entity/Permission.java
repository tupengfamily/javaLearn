package com.learning.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * 权限实体
 * <p>
 * 权限编码(code)用于代码中的鉴权,如 "user:list"、"role:edit"。
 * 类型(type)用于前端菜单/按钮渲染: MENU / BUTTON / API
 */
@Entity
@Table(name = "t_permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 权限编码(唯一): user:list、role:edit 等 */
    @Column(nullable = false, unique = true, length = 100)
    private String code;

    /** 权限显示名 */
    @Column(nullable = false, length = 50)
    private String name;

    /** 类型: MENU 菜单 / BUTTON 按钮 / API 接口 */
    @Column(nullable = false, length = 20)
    private String type;

    @Column(length = 200)
    private String description;

    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    public Permission() {}

    public Permission(String code, String name, String type) {
        this.code = code;
        this.name = name;
        this.type = type;
    }

    @jakarta.persistence.PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now();
    }

    // ============== Getter / Setter ==============

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}