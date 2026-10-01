package com.learning.springboot.dto;

import com.learning.springboot.entity.Permission;

import java.time.LocalDateTime;

/**
 * 权限 DTO
 */
public class PermissionDTO {

    private Long id;
    private String code;
    private String name;
    private String type;
    private String description;
    private LocalDateTime createTime;

    public static PermissionDTO from(Permission p) {
        PermissionDTO d = new PermissionDTO();
        d.id = p.getId();
        d.code = p.getCode();
        d.name = p.getName();
        d.type = p.getType();
        d.description = p.getDescription();
        d.createTime = p.getCreateTime();
        return d;
    }

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