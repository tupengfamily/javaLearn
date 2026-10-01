package com.learning.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.springboot.dto.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * UserController Web 层测试
 * <p>
 * @AutoConfigureMockMvc 自动配置 MockMvc,模拟 HTTP 请求。
 * 优势:不需要启动真实 Tomcat,速度快。
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("UserController Web 测试")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/users - 查询所有用户")
    void testListAll() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("查询成功"));
    }

    @Test
    @DisplayName("POST /api/users - 创建用户成功")
    void testCreateUser() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("newuser");
        dto.setEmail("new@example.com");
        dto.setAge(25);
        dto.setAddress("测试地址");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("创建成功"))
                .andExpect(jsonPath("$.data.username").value("newuser"));
    }

    @Test
    @DisplayName("POST /api/users - 邮箱格式错误返回 400")
    void testCreateUserInvalidEmail() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("invaliduser");
        dto.setEmail("invalid-email");  // 不是合法邮箱
        dto.setAge(25);

        MvcResult result = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertTrue(response.contains("邮箱格式不正确") || response.contains("email"));
    }

    @Test
    @DisplayName("POST /api/users - 用户名为空返回 400")
    void testCreateUserEmptyUsername() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("");  // 空字符串
        dto.setEmail("test@example.com");
        dto.setAge(25);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/users/{id} - 查询不存在的用户返回业务错误")
    void testGetByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/users/99999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("用户不存在")));
    }

    @Test
    @DisplayName("GET /api/hello - 简单接口")
    void testHello() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(containsString("Hello")));
    }
}