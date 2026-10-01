package com.learning.springboot.service;

import com.learning.springboot.dto.LoginRequest;
import com.learning.springboot.dto.LoginResponse;
import com.learning.springboot.dto.RegisterRequest;

/**
 * 认证服务接口
 */
public interface AuthService {

    /** 用户登录,返回 Token + 用户信息 */
    LoginResponse login(LoginRequest req, String clientIp);

    /** 用户注册 */
    LoginResponse register(RegisterRequest req, String clientIp);

    /** 获取当前登录用户的信息 */
    LoginResponse me(Long userId);
}