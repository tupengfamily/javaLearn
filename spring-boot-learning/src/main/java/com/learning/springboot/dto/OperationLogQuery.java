package com.learning.springboot.dto;

import java.time.LocalDateTime;

/**
 * 操作日志查询条件
 */
public class OperationLogQuery {

    private String module;
    private String username;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer page = 1;
    private Integer size = 20;

    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }
    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }
}