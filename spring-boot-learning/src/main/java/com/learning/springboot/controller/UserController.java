package com.learning.springboot.controller;

import com.learning.springboot.dto.UserDTO;
import com.learning.springboot.entity.User;
import com.learning.springboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户 REST 控制器
 * <p>
 * 关键注解:
 * - @RestController = @Controller + @ResponseBody
 * - @RequestMapping: 定义请求路径前缀
 * - @GetMapping / @PostMapping / @PutMapping / @DeleteMapping: HTTP 方法
 * - @PathVariable: 从 URL 中取参数
 * - @RequestParam: 从查询字符串取参数
 * - @RequestBody: 从请求体取 JSON
 * - @Valid: 启用参数校验
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /api/users - 查询所有用户
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> listAll() {
        List<User> users = userService.listAll();
        return success("查询成功", users);
    }

    /**
     * GET /api/users/{id} - 按 ID 查询
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return success("查询成功", user);
    }

    /**
     * POST /api/users - 创建用户
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody UserDTO userDTO) {
        User created = userService.createUser(userDTO);
        return success("创建成功", created);
    }

    /**
     * PUT /api/users/{id} - 更新用户
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {
        User updated = userService.updateUser(id, userDTO);
        return success("更新成功", updated);
    }

    /**
     * DELETE /api/users/{id} - 删除用户
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return success("删除成功", null);
    }

    /**
     * GET /api/users/search?keyword=xxx - 按用户名模糊查询
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            @RequestParam(required = false, defaultValue = "") String keyword) {
        List<User> users = userService.searchByUsername(keyword);
        return success("查询成功", users);
    }

    /**
     * GET /api/users/age?min=18&max=30 - 按年龄范围查询
     */
    @GetMapping("/age")
    public ResponseEntity<Map<String, Object>> findByAge(
            @RequestParam Integer min,
            @RequestParam Integer max) {
        List<User> users = userService.findByAgeRange(min, max);
        return success("查询成功", users);
    }

    /**
     * 统一响应格式
     */
    private ResponseEntity<Map<String, Object>> success(String message, Object data) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", 200);
        body.put("message", message);
        body.put("data", data);
        body.put("timestamp", java.time.LocalDateTime.now());
        return ResponseEntity.ok(body);
    }
}