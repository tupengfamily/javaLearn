package com.learning.springboot.service.impl;

import com.learning.springboot.config.JwtProperties;
import com.learning.springboot.dto.LoginRequest;
import com.learning.springboot.dto.LoginResponse;
import com.learning.springboot.dto.RegisterRequest;
import com.learning.springboot.entity.Role;
import com.learning.springboot.entity.User;
import com.learning.springboot.exception.BusinessException;
import com.learning.springboot.repository.PermissionRepository;
import com.learning.springboot.repository.RoleRepository;
import com.learning.springboot.repository.UserRepository;
import com.learning.springboot.security.JwtUtil;
import com.learning.springboot.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 认证服务实现
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PermissionRepository permissionRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private JwtProperties jwtProperties;

    @Override
    @Transactional
    public LoginResponse login(LoginRequest req, String clientIp) {
        User user = userRepository.findByUsernameWithRoles(req.getUsername())
            .orElseThrow(() -> new BusinessException(401, "用户名或密码错误"));

        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BusinessException(403, "账号已被禁用");
        }

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new BusinessException(401, "用户名或密码错误");
        }

        // 提取角色编码
        Set<String> roleCodes = new HashSet<>();
        for (Role r : user.getRoles()) roleCodes.add(r.getCode());

        // 颁发 Token
        String token = jwtUtil.generate(user.getId(), user.getUsername(), roleCodes);

        // 更新登录信息
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(clientIp);
        userRepository.save(user);

        // 加载权限
        List<String> perms = permissionRepository.findPermissionCodesByUserId(user.getId());

        LoginResponse resp = new LoginResponse();
        resp.setToken(token);
        resp.setExpiresIn(jwtProperties.getExpirationMillis() / 1000);
        resp.setUserId(user.getId());
        resp.setUsername(user.getUsername());
        resp.setEmail(user.getEmail());
        resp.setStatus(user.getStatus());
        resp.setRoles(roleCodes);
        resp.setPermissions(new HashSet<>(perms));
        return resp;
    }

    @Override
    @Transactional
    public LoginResponse register(RegisterRequest req, String clientIp) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new BusinessException(409, "用户名已存在: " + req.getUsername());
        }
        if (req.getEmail() != null && userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new BusinessException(409, "邮箱已被注册");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setEmail(req.getEmail());
        user.setAge(req.getAge() != null ? req.getAge() : 18);
        user.setAddress(req.getAddress());
        user.setStatus(1);

        // 默认赋予 USER 角色
        Role defaultRole = roleRepository.findByCode("USER")
            .orElseThrow(() -> new BusinessException(500, "默认角色 USER 不存在,请先初始化"));
        user.setRoles(new HashSet<>(Set.of(defaultRole)));

        User saved = userRepository.save(user);

        // 自动登录: 颁发 token
        Set<String> roleCodes = new HashSet<>(Set.of("USER"));
        String token = jwtUtil.generate(saved.getId(), saved.getUsername(), roleCodes);

        LoginResponse resp = new LoginResponse();
        resp.setToken(token);
        resp.setExpiresIn(jwtProperties.getExpirationMillis() / 1000);
        resp.setUserId(saved.getId());
        resp.setUsername(saved.getUsername());
        resp.setEmail(saved.getEmail());
        resp.setStatus(saved.getStatus());
        resp.setRoles(roleCodes);
        resp.setPermissions(new HashSet<>());
        return resp;
    }

    @Override
    public LoginResponse me(Long userId) {
        User user = userRepository.findByUsernameWithRoles(getUsername(userId))
            .orElseThrow(() -> new BusinessException(404, "用户不存在"));

        Set<String> roleCodes = new HashSet<>();
        for (Role r : user.getRoles()) roleCodes.add(r.getCode());

        List<String> perms = permissionRepository.findPermissionCodesByUserId(user.getId());

        LoginResponse resp = new LoginResponse();
        resp.setUserId(user.getId());
        resp.setUsername(user.getUsername());
        resp.setEmail(user.getEmail());
        resp.setStatus(user.getStatus());
        resp.setRoles(roleCodes);
        resp.setPermissions(new HashSet<>(perms));
        return resp;
    }

    private String getUsername(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(404, "用户不存在"))
            .getUsername();
    }
}