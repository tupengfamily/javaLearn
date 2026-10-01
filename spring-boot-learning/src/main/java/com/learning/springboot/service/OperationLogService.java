package com.learning.springboot.service;

import com.learning.springboot.dto.OperationLogQuery;
import com.learning.springboot.dto.PageResult;
import com.learning.springboot.entity.OperationLog;

/**
 * 操作日志服务接口
 */
public interface OperationLogService {

    /**
     * 异步保存日志
     * (由 AOP 调用;事务传播 REQUIRES_NEW,即使业务事务回滚也能落库)
     */
    void save(OperationLog log);

    PageResult<OperationLog> pageQuery(OperationLogQuery query);

    /** 删除指定时间之前的日志 */
    int cleanBefore(java.time.LocalDateTime before);
}