package com.learning.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * 操作日志实体
 * <p>
 * 记录用户的每一次写操作(增删改),由 AOP 切面自动写入。
 * 用于审计、问题排查、行为分析。
 */
@Entity
@Table(name = "t_operation_log")
public class OperationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(length = 50)
    private String username;

    @Column(length = 50)
    private String module;

    @Column(length = 50)
    private String action;

    @Column(length = 10)
    private String requestMethod;

    @Column(length = 255)
    private String requestUrl;

    @Column(length = 2000)
    private String requestParams;

    @Column(length = 2000)
    private String responseResult;

    @Column(length = 50)
    private String ip;

    @Column(length = 255)
    private String userAgent;

    /** 1=成功, 0=失败 */
    @Column(nullable = false)
    private Integer status = 1;

    @Column(length = 2000)
    private String errorMsg;

    @Column(name = "cost_ms")
    private Long costMs;

    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @jakarta.persistence.PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now();
        if (this.status == null) this.status = 1;
    }

    // ============== Getter / Setter ==============

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getRequestMethod() { return requestMethod; }
    public void setRequestMethod(String requestMethod) { this.requestMethod = requestMethod; }

    public String getRequestUrl() { return requestUrl; }
    public void setRequestUrl(String requestUrl) { this.requestUrl = requestUrl; }

    public String getRequestParams() { return requestParams; }
    public void setRequestParams(String requestParams) { this.requestParams = requestParams; }

    public String getResponseResult() { return responseResult; }
    public void setResponseResult(String responseResult) { this.responseResult = responseResult; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public String getErrorMsg() { return errorMsg; }
    public void setErrorMsg(String errorMsg) { this.errorMsg = errorMsg; }

    public Long getCostMs() { return costMs; }
    public void setCostMs(Long costMs) { this.costMs = costMs; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}