package com.learning.springboot.service;

import com.learning.springboot.dto.UserDTO;
import com.learning.springboot.entity.User;
import com.learning.springboot.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserService 测试
 * <p>
 * 演示:
 * - @SpringBootTest: 启动完整 Spring 上下文
 * - @Transactional: 测试方法在事务中执行,测试后自动回滚
 * - @Rollback: 显式控制是否回滚
 */
@SpringBootTest
@Transactional
@Rollback  // 测试后回滚,不污染数据库
@DisplayName("UserService 集成测试")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("创建用户成功")
    void testCreateUser() {
        UserDTO dto = new UserDTO();
        dto.setUsername("测试用户");
        dto.setEmail("test@example.com");
        dto.setAge(20);
        dto.setAddress("测试地址");

        User created = userService.createUser(dto);
        assertNotNull(created.getId());
        assertEquals("测试用户", created.getUsername());
        assertNotNull(created.getCreateTime());
    }

    @Test
    @DisplayName("创建用户时用户名重复抛异常")
    void testCreateDuplicateUsername() {
        UserDTO dto = new UserDTO();
        dto.setUsername("张三");  // data.sql 中已存在
        dto.setEmail("test@example.com");
        dto.setAge(20);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> userService.createUser(dto));
        assertTrue(ex.getMessage().contains("用户名已存在"));
    }

    @Test
    @DisplayName("按 ID 查询用户")
    void testGetById() {
        // 假设 data.sql 中插入了 id=1 的张三
        // 因为有 @Rollback,我们在测试中先创建一个
        UserDTO dto = new UserDTO();
        dto.setUsername("queryuser");
        dto.setEmail("q@example.com");
        dto.setAge(25);
        User created = userService.createUser(dto);

        User found = userService.getById(created.getId());
        assertEquals(created.getId(), found.getId());
        assertEquals("queryuser", found.getUsername());
    }

    @Test
    @DisplayName("按 ID 查询不存在的用户抛异常")
    void testGetByIdNotFound() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> userService.getById(99999L));
        assertTrue(ex.getMessage().contains("用户不存在"));
    }

    @Test
    @DisplayName("更新用户")
    void testUpdateUser() {
        UserDTO createDto = new UserDTO();
        createDto.setUsername("updatetest");
        createDto.setEmail("u@example.com");
        createDto.setAge(20);
        User created = userService.createUser(createDto);

        UserDTO updateDto = new UserDTO();
        updateDto.setUsername("updatedname");
        updateDto.setEmail("updated@example.com");
        updateDto.setAge(30);
        updateDto.setAddress("新地址");

        User updated = userService.updateUser(created.getId(), updateDto);
        assertEquals("updatedname", updated.getUsername());
        assertEquals(30, updated.getAge());
    }

    @Test
    @DisplayName("删除用户")
    void testDeleteUser() {
        UserDTO dto = new UserDTO();
        dto.setUsername("deletetest");
        dto.setEmail("d@example.com");
        dto.setAge(20);
        User created = userService.createUser(dto);

        userService.deleteUser(created.getId());

        BusinessException ex = assertThrows(BusinessException.class,
                () -> userService.getById(created.getId()));
        assertTrue(ex.getMessage().contains("用户不存在"));
    }

    @Test
    @DisplayName("按年龄范围查询")
    void testFindByAgeRange() {
        // 准备测试数据
        for (int i = 0; i < 3; i++) {
            UserDTO dto = new UserDTO();
            dto.setUsername("age" + i);
            dto.setEmail("age" + i + "@example.com");
            dto.setAge(20 + i);
            userService.createUser(dto);
        }

        // 查询 20~22 范围(可能包含 data.sql 中的王五,年龄22)
        List<User> users = userService.findByAgeRange(20, 22);
        assertTrue(users.size() >= 3); // 至少 3 个
    }

    @Test
    @DisplayName("按用户名模糊查询")
    void testSearchByUsername() {
        // 准备测试数据
        for (int i = 0; i < 3; i++) {
            UserDTO dto = new UserDTO();
            dto.setUsername("search" + i);
            dto.setEmail("s" + i + "@example.com");
            dto.setAge(20);
            userService.createUser(dto);
        }

        List<User> users = userService.searchByUsername("search");
        assertTrue(users.size() >= 3);
    }
}