package com.learning.springboot.service;

import com.learning.springboot.dto.RoleDTO;
import com.learning.springboot.dto.RoleUpsertRequest;
import com.learning.springboot.entity.Role;

import java.util.List;
import java.util.Set;

/**
 * 角色服务接口
 */
public interface RoleService {

    List<RoleDTO> listAll();

    RoleDTO getById(Long id);

    RoleDTO create(RoleUpsertRequest req);

    RoleDTO update(Long id, RoleUpsertRequest req);

    void delete(Long id);

    void assignPermissions(Long roleId, Set<Long> permissionIds);

    Role getEntityById(Long id);
}