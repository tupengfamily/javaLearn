package com.learning.springboot.service;

import java.util.List;
import java.util.Map;

/**
 * 仪表盘服务接口
 */
public interface DashboardService {

    /** 仪表盘首页关键指标 */
    Map<String, Object> summary();

    /** 最近 7 天每天新增用户 */
    List<Map<String, Object>> last7DaysRegistrations();

    /** 最近 7 天每天操作次数 */
    List<Map<String, Object>> last7DaysOperations();

    /** 用户状态分布 */
    List<Map<String, Object>> userStatusDistribution();
}