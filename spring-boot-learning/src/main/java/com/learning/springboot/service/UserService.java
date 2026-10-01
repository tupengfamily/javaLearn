package com.learning.springboot.service;

import com.learning.springboot.dto.UserDTO;
import com.learning.springboot.entity.User;

import java.util.List;

/**
 * 用户 Service 接口
 * <p>
 * 为什么要面向接口编程:
 * 1. 解耦:Controller 不依赖具体实现
 * 2. 便于测试:可以用 Mock 替换真实实现
 * 3. 便于扩展:可以有不同的实现(如缓存实现)
 */
public interface UserService {

    /**
     * 创建用户
     */
    User createUser(UserDTO userDTO);

    /**
     * 更新用户
     */
    User updateUser(Long id, UserDTO userDTO);

    /**
     * 删除用户
     */
    void deleteUser(Long id);

    /**
     * 按 ID 查询
     */
    User getById(Long id);

    /**
     * 查询所有
     */
    List<User> listAll();

    /**
     * 按用户名模糊查询
     */
    List<User> searchByUsername(String keyword);

    /**
     * 按年龄范围查询
     */
    List<User> findByAgeRange(Integer min, Integer max);
}