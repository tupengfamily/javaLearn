package com.learning.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

/**
 * 创建/更新角色请求
 */
public class RoleUpsertRequest {

    @NotBlank(message = "角色编码不能为空")
    @Size(max = 50)
    private String code;

    @NotBlank(message = "角色名称不能为空")
    @Size(max = 50)
    private String name;

    @Size(max = 200)
    private String description;

    /** 分配的权限 ID 集合(可选) */
    private Set<Long> permissionIds;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Set<Long> getPermissionIds() { return permissionIds; }
    public void setPermissionIds(Set<Long> permissionIds) { this.permissionIds = permissionIds; }
}