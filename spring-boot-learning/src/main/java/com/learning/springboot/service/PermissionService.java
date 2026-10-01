package com.learning.springboot.service;

import com.learning.springboot.dto.PermissionDTO;
import com.learning.springboot.entity.Permission;

import java.util.List;

/**
 * 权限服务接口
 */
public interface PermissionService {

    List<PermissionDTO> listAll();

    PermissionDTO create(Permission p);

    void delete(Long id);
}