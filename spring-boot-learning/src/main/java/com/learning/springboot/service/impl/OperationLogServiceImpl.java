package com.learning.springboot.service.impl;

import com.learning.springboot.dto.OperationLogQuery;
import com.learning.springboot.dto.PageResult;
import com.learning.springboot.entity.OperationLog;
import com.learning.springboot.repository.OperationLogRepository;
import com.learning.springboot.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired private OperationLogRepository repo;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(OperationLog log) {
        if (log == null) return;
        repo.save(log);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResult<OperationLog> pageQuery(OperationLogQuery query) {
        int page = query.getPage() == null || query.getPage() < 1 ? 1 : query.getPage();
        int size = query.getSize() == null || query.getSize() < 1 ? 20 : Math.min(query.getSize(), 100);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<OperationLog> p = repo.search(
            blankToNull(query.getModule()),
            blankToNull(query.getUsername()),
            query.getStartTime(),
            query.getEndTime(),
            pageable);
        return PageResult.of(p);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int cleanBefore(java.time.LocalDateTime before) {
        return repo.deleteBefore(before);
    }

    private static String blankToNull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }
}