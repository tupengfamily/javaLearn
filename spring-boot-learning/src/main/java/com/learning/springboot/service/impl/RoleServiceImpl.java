package com.learning.springboot.service.impl;

import com.learning.springboot.dto.RoleDTO;
import com.learning.springboot.dto.RoleUpsertRequest;
import com.learning.springboot.entity.Permission;
import com.learning.springboot.entity.Role;
import com.learning.springboot.exception.BusinessException;
import com.learning.springboot.repository.PermissionRepository;
import com.learning.springboot.repository.RoleRepository;
import com.learning.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色服务实现
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired private RoleRepository roleRepository;
    @Autowired private PermissionRepository permissionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> listAll() {
        // JOIN FETCH 让权限一起加载,避免 DTO 转换时懒加载失效
        return roleRepository.findAllWithPermissions().stream().map(RoleDTO::from).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDTO getById(Long id) {
        Role role = roleRepository.findByIdWithPermissions(id)
            .orElseThrow(() -> new BusinessException(404, "角色不存在: id=" + id));
        return RoleDTO.from(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDTO create(RoleUpsertRequest req) {
        if (roleRepository.existsByCode(req.getCode())) {
            throw new BusinessException(409, "角色编码已存在: " + req.getCode());
        }
        Role role = new Role(req.getCode(), req.getName(), req.getDescription());
        if (req.getPermissionIds() != null && !req.getPermissionIds().isEmpty()) {
            role.setPermissions(new HashSet<>(permissionRepository.findAllById(req.getPermissionIds())));
        }
        return RoleDTO.from(roleRepository.save(role));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDTO update(Long id, RoleUpsertRequest req) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new BusinessException(404, "角色不存在"));
        if (req.getName() != null) role.setName(req.getName());
        if (req.getDescription() != null) role.setDescription(req.getDescription());
        if (req.getPermissionIds() != null) {
            role.setPermissions(new HashSet<>(permissionRepository.findAllById(req.getPermissionIds())));
        }
        return RoleDTO.from(roleRepository.save(role));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new BusinessException(404, "角色不存在");
        }
        // 不允许删除 ADMIN
        Role role = roleRepository.findById(id).get();
        if ("ADMIN".equals(role.getCode())) {
            throw new BusinessException(400, "系统内置角色 ADMIN 不能删除");
        }
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignPermissions(Long roleId, Set<Long> permissionIds) {
        Role role = roleRepository.findByIdWithPermissions(roleId)
            .orElseThrow(() -> new BusinessException(404, "角色不存在"));
        if (permissionIds == null) permissionIds = Set.of();
        Set<Permission> perms = new HashSet<>(permissionRepository.findAllById(permissionIds));
        role.setPermissions(perms);
        roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getEntityById(Long id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new BusinessException(404, "角色不存在"));
    }
}