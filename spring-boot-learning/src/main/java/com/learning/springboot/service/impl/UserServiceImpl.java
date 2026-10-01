package com.learning.springboot.service.impl;

import com.learning.springboot.dto.UserDTO;
import com.learning.springboot.entity.User;
import com.learning.springboot.exception.BusinessException;
import com.learning.springboot.repository.UserRepository;
import com.learning.springboot.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * UserService 实现类
 * <p>
 * 关键注解:
 * - @Service: 标记为业务层组件,Spring 会自动创建实例并管理
 * - @Transactional: 事务管理(方法执行要么全成功,要么全失败)
 * - @Autowired: 自动注入依赖(可省略,Spring 4+ 默认按类型注入)
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * 构造器注入(推荐)
     * <p>
     * 优点:
     * 1. 依赖明确,易于测试
     * 2. 字段可声明为 final,保证不可变
     * 3. 避免循环依赖问题
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User createUser(UserDTO userDTO) {
        // 业务校验:用户名不能重复
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new BusinessException("用户名已存在: " + userDTO.getUsername());
        }

        User user = new User();
        BeanUtils.copyProperties(userDTO, user); // 把 DTO 属性拷贝到 Entity
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long id, UserDTO userDTO) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("用户不存在: id=" + id));

        // 检查用户名是否被其他人占用
        userRepository.findByUsername(userDTO.getUsername())
                .ifPresent(u -> {
                    if (!u.getId().equals(id)) {
                        throw new BusinessException("用户名已被其他用户占用: " + userDTO.getUsername());
                    }
                });

        existing.setUsername(userDTO.getUsername());
        existing.setEmail(userDTO.getEmail());
        existing.setAge(userDTO.getAge());
        existing.setAddress(userDTO.getAddress());

        return userRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BusinessException("用户不存在: id=" + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("用户不存在: id=" + id));
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> searchByUsername(String keyword) {
        return userRepository.searchByUsernameLike(keyword);
    }

    @Override
    public List<User> findByAgeRange(Integer min, Integer max) {
        return userRepository.findByAgeBetween(min, max);
    }
}