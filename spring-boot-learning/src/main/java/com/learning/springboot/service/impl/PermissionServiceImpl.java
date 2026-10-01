package com.learning.springboot.service.impl;

import com.learning.springboot.dto.PermissionDTO;
import com.learning.springboot.entity.Permission;
import com.learning.springboot.exception.BusinessException;
import com.learning.springboot.repository.PermissionRepository;
import com.learning.springboot.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired private PermissionRepository permissionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDTO> listAll() {
        return permissionRepository.findAll().stream().map(PermissionDTO::from).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PermissionDTO create(Permission p) {
        if (p.getCode() == null || p.getCode().isBlank()) {
            throw new BusinessException(400, "权限编码不能为空");
        }
        return PermissionDTO.from(permissionRepository.save(p));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new BusinessException(404, "权限不存在");
        }
        permissionRepository.deleteById(id);
    }
}