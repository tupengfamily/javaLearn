package com.learning.springboot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 用户数据传输对象(DTO)
 * <p>
 * 为什么要用 DTO:
 * 1. 隔离 Entity 与外部接口,避免暴露内部字段(如密码)
 * 2. 自定义校验规则
 * 3. 灵活控制字段(返回哪些字段、接收哪些字段)
 * <p>
 * 校验注解:
 * - @NotBlank: 不能为空字符串
 * - @Size: 长度限制
 * - @Email: 邮箱格式
 * - @Min / @Max: 数值范围
 */
public class UserDTO {

    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 50, message = "用户名长度必须在 2~50 之间")
    private String username;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Min(value = 0, message = "年龄不能小于0")
    @Max(value = 150, message = "年龄不能大于150")
    private Integer age;

    @Size(max = 200, message = "地址长度不能超过200")
    private String address;

    public UserDTO() {}

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
}