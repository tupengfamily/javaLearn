package com.learning.springboot.dto;

import com.learning.springboot.entity.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户视图对象(返回给前端,不包含密码)
 */
public class UserVO {

    private Long id;
    private String username;
    private String email;
    private Integer age;
    private String address;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime lastLoginTime;
    private String lastLoginIp;
    private Set<String> roleCodes;
    private Set<String> roleNames;

    public static UserVO from(User u) {
        UserVO v = new UserVO();
        v.id = u.getId();
        v.username = u.getUsername();
        v.email = u.getEmail();
        v.age = u.getAge();
        v.address = u.getAddress();
        v.status = u.getStatus();
        v.createTime = u.getCreateTime();
        v.updateTime = u.getUpdateTime();
        v.lastLoginTime = u.getLastLoginTime();
        v.lastLoginIp = u.getLastLoginIp();
        v.roleCodes = new HashSet<>();
        v.roleNames = new HashSet<>();
        if (u.getRoles() != null) {
            for (var r : u.getRoles()) {
                v.roleCodes.add(r.getCode());
                v.roleNames.add(r.getName());
            }
        }
        return v;
    }

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
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
    public LocalDateTime getLastLoginTime() { return lastLoginTime; }
    public void setLastLoginTime(LocalDateTime lastLoginTime) { this.lastLoginTime = lastLoginTime; }
    public String getLastLoginIp() { return lastLoginIp; }
    public void setLastLoginIp(String lastLoginIp) { this.lastLoginIp = lastLoginIp; }
    public Set<String> getRoleCodes() { return roleCodes; }
    public void setRoleCodes(Set<String> roleCodes) { this.roleCodes = roleCodes; }
    public Set<String> getRoleNames() { return roleNames; }
    public void setRoleNames(Set<String> roleNames) { this.roleNames = roleNames; }
}