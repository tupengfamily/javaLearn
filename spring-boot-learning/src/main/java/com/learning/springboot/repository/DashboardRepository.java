package com.learning.springboot.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 仪表盘聚合查询
 * <p>
 * 全部使用原生 SQL,因为聚合/日期函数高度依赖方言(SQLite / MySQL 各异)。
 * 不继承 JpaRepository,直接使用 EntityManager。
 */
@Repository
public class DashboardRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * 仪表盘首页统计: 总用户/启用用户/总角色/今日操作数
     * 一次查询返回多行(每行一个统计),前端按需取值。
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> dashboardSummary() {
        String sql = """
            SELECT
              (SELECT COUNT(*) FROM t_user)                                  AS totalUsers,
              (SELECT COUNT(*) FROM t_user WHERE status = 1)                 AS activeUsers,
              (SELECT COUNT(*) FROM t_user WHERE status = 0)                 AS disabledUsers,
              (SELECT COUNT(*) FROM t_role)                                  AS totalRoles,
              (SELECT COUNT(*) FROM t_permission)                            AS totalPermissions,
              (SELECT COUNT(*) FROM t_operation_log
                 WHERE date(create_time) = date('now', 'localtime'))         AS todayOps,
              (SELECT COUNT(*) FROM t_operation_log)                         AS totalOps
            """;
        List<Object[]> rows = em.createNativeQuery(sql).getResultList();
        Map<String, Object> result = new LinkedHashMap<>();
        if (!rows.isEmpty()) {
            Object[] r = rows.get(0);
            result.put("totalUsers", toLong(r[0]));
            result.put("activeUsers", toLong(r[1]));
            result.put("disabledUsers", toLong(r[2]));
            result.put("totalRoles", toLong(r[3]));
            result.put("totalPermissions", toLong(r[4]));
            result.put("todayOps", toLong(r[5]));
            result.put("totalOps", toLong(r[6]));
        }
        return result;
    }

    /**
     * 最近 7 天每天新增用户(SQLite: strftime 按 YYYY-MM-DD 分桶)
     * 自动补齐 7 天中缺失的日期(count=0)
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> last7DaysRegistrations() {
        return last7DaysBucket("t_user");
    }

    /** 最近 7 天每天操作次数 */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> last7DaysOperations() {
        return last7DaysBucket("t_operation_log");
    }

    private List<Map<String, Object>> last7DaysBucket(String table) {
        String sql = String.format("""
            SELECT date(create_time) AS day, COUNT(*) AS cnt
            FROM %s
            WHERE create_time >= datetime('now', '-7 days', 'localtime')
            GROUP BY date(create_time)
            ORDER BY day ASC
            """, table);
        List<Object[]> rows = em.createNativeQuery(sql).getResultList();

        // 用 Map<LocalDate, Long> 聚合,再补齐最近 7 天
        Map<LocalDate, Long> bucket = new LinkedHashMap<>();
        for (Object[] r : rows) {
            String day = (String) r[0];
            Long cnt = toLong(r[1]);
            bucket.put(LocalDate.parse(day), cnt);
        }

        List<Map<String, Object>> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate d = today.minusDays(i);
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("date", d.toString());
            item.put("count", bucket.getOrDefault(d, 0L));
            result.add(item);
        }
        return result;
    }

    /** 用户状态分布 */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> userStatusDistribution() {
        Query q = em.createNativeQuery("SELECT status, COUNT(*) FROM t_user GROUP BY status");
        List<Object[]> rows = q.getResultList();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] r : rows) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("status", r[0]);
            item.put("count", toLong(r[1]));
            result.add(item);
        }
        return result;
    }

    private static long toLong(Object v) {
        if (v == null) return 0L;
        if (v instanceof Number n) return n.longValue();
        return Long.parseLong(v.toString());
    }
}