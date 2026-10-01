package com.learning.springboot.repository;

import com.learning.springboot.entity.OperationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作日志 Repository
 */
@Repository
public interface OperationLogRepository extends JpaRepository<OperationLog, Long> {

    // 派生方法: 按用户名 + 模块分页查询
    Page<OperationLog> findByUsernameContainingAndModuleAndCreateTimeBetween(
        String username, String module, LocalDateTime start, LocalDateTime end, Pageable pageable);

    // 派生方法: 按时间区间分页
    Page<OperationLog> findByCreateTimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    // 派生方法: 按模块分页
    Page<OperationLog> findByModule(String module, Pageable pageable);

    // JPQL: 动态条件分页查询
    @Query("""
        SELECT o FROM OperationLog o
        WHERE (:module IS NULL OR o.module = :module)
          AND (:username IS NULL OR o.username LIKE %:username%)
          AND (:start IS NULL OR o.createTime >= :start)
          AND (:end IS NULL OR o.createTime <= :end)
        ORDER BY o.createTime DESC
        """)
    Page<OperationLog> search(@Param("module") String module,
                              @Param("username") String username,
                              @Param("start") LocalDateTime start,
                              @Param("end") LocalDateTime end,
                              Pageable pageable);

    // JPQL: 删除某时间之前的日志(用于定期清理)
    @Modifying
    @Query("DELETE FROM OperationLog o WHERE o.createTime < :before")
    int deleteBefore(@Param("before") LocalDateTime before);

    // 原生 SQL: 按模块聚合(统计每个模块的操作次数)
    @Query(value = """
        SELECT module, COUNT(*) AS cnt
        FROM t_operation_log
        WHERE create_time >= :since
        GROUP BY module
        ORDER BY cnt DESC
        """, nativeQuery = true)
    List<Object[]> countGroupByModuleSince(@Param("since") LocalDateTime since);
}