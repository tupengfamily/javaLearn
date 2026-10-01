package com.learning.springboot.repository;

import com.learning.springboot.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 角色 Repository
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // 派生方法
    Optional<Role> findByCode(String code);

    boolean existsByCode(String code);

    List<Role> findByCodeIn(Collection<String> codes);

    // JPQL: 关联查询权限
    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions WHERE r.id = :id")
    Optional<Role> findByIdWithPermissions(@Param("id") Long id);

    // JPQL: 一次查所有角色并 JOIN FETCH 权限(避免 N+1)
    @Query("SELECT DISTINCT r FROM Role r LEFT JOIN FETCH r.permissions ORDER BY r.id")
    List<Role> findAllWithPermissions();

    // JPQL: 关联查询权限(按编码)
    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions WHERE r.code = :code")
    Optional<Role> findByCodeWithPermissions(@Param("code") String code);

    // 原生 SQL: 查询拥有指定权限的所有角色
    @Query(value = """
        SELECT r.* FROM t_role r
        JOIN t_role_permission rp ON r.id = rp.role_id
        WHERE rp.permission_id = :permissionId
        """, nativeQuery = true)
    List<Role> findRolesHoldingPermission(@Param("permissionId") Long permissionId);
}