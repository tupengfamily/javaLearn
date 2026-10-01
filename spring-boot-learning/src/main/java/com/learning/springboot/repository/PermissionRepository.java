package com.learning.springboot.repository;

import com.learning.springboot.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限 Repository
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    // 派生方法
    List<Permission> findByType(String type);

    List<Permission> findByCodeIn(List<String> codes);

    // JPQL: 按角色编码查询权限
    @Query("""
        SELECT p FROM Permission p
        WHERE p.id IN (
          SELECT rp.id FROM Role r JOIN r.permissions rp WHERE r.code = :code
        )
        """)
    List<Permission> findByRoleCode(@Param("code") String code);

    // 原生 SQL: 查询某用户的所有权限编码(三表 JOIN)
    @Query(value = """
        SELECT DISTINCT p.code FROM t_permission p
        JOIN t_role_permission rp ON p.id = rp.permission_id
        JOIN t_user_role ur ON rp.role_id = ur.role_id
        WHERE ur.user_id = :userId
        """, nativeQuery = true)
    List<String> findPermissionCodesByUserId(@Param("userId") Long userId);
}