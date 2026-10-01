package com.learning.springboot.service;

import com.learning.springboot.dto.ChangePasswordRequest;
import com.learning.springboot.dto.PageResult;
import com.learning.springboot.dto.UserVO;
import com.learning.springboot.entity.User;

import java.util.List;
import java.util.Set;

/**
 * 用户服务接口
 */
public interface UserService {

    UserVO createUser(String username, String password, String email, Integer age, String address);

    UserVO updateUser(Long id, String email, Integer age, String address, Integer status);

    void deleteUser(Long id);

    UserVO getById(Long id);

    PageResult<UserVO> pageUsers(String keyword, Integer status, int page, int size);

    /** 修改密码: 验证旧密码,设置新密码 */
    void changePassword(Long userId, ChangePasswordRequest req);

    /** 分配角色(覆盖式) */
    void assignRoles(Long userId, Set<String> roleCodes);

    /** 启用/禁用用户 */
    void updateStatus(Long userId, Integer status);

    List<User> listAll();

    List<User> searchByUsername(String keyword);

    List<User> findByAgeRange(Integer min, Integer max);
}