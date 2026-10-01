package com.learning.springboot.dto;

import com.learning.springboot.entity.Role;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色 DTO(返回给前端,含权限编码列表)
 */
public class RoleDTO {

    private Long id;
    private String code;
    private String name;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Set<String> permissionCodes;
    private Set<String> permissionNames;

    public static RoleDTO from(Role r) {
        RoleDTO d = new RoleDTO();
        d.id = r.getId();
        d.code = r.getCode();
        d.name = r.getName();
        d.description = r.getDescription();
        d.createTime = r.getCreateTime();
        d.updateTime = r.getUpdateTime();
        d.permissionCodes = new HashSet<>();
        d.permissionNames = new HashSet<>();
        if (r.getPermissions() != null) {
            for (var p : r.getPermissions()) {
                d.permissionCodes.add(p.getCode());
                d.permissionNames.add(p.getName());
            }
        }
        return d;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
    public Set<String> getPermissionCodes() { return permissionCodes; }
    public void setPermissionCodes(Set<String> permissionCodes) { this.permissionCodes = permissionCodes; }
    public Set<String> getPermissionNames() { return permissionNames; }
    public void setPermissionNames(Set<String> permissionNames) { this.permissionNames = permissionNames; }
}