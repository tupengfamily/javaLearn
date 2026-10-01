package com.learning.springboot.dto;

import com.learning.springboot.entity.DictType;

import java.time.LocalDateTime;

/**
 * 字典类型 DTO
 */
public class DictTypeDTO {

    private Long id;
    private String typeCode;
    private String typeName;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static DictTypeDTO from(DictType t) {
        DictTypeDTO d = new DictTypeDTO();
        d.id = t.getId();
        d.typeCode = t.getTypeCode();
        d.typeName = t.getTypeName();
        d.description = t.getDescription();
        d.status = t.getStatus();
        d.createTime = t.getCreateTime();
        d.updateTime = t.getUpdateTime();
        return d;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTypeCode() { return typeCode; }
    public void setTypeCode(String typeCode) { this.typeCode = typeCode; }
    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
}