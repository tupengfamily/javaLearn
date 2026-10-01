package com.learning.springboot.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

/**
 * 分配角色请求
 */
public class AssignRoleRequest {

    @NotEmpty(message = "至少选择一个角色")
    private Set<String> roleCodes;

    public Set<String> getRoleCodes() { return roleCodes; }
    public void setRoleCodes(Set<String> roleCodes) { this.roleCodes = roleCodes; }
}