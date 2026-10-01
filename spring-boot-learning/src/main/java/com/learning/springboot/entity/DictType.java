package com.learning.springboot.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典类型实体
 * <p>
 * 表示一类字典,如 "用户状态"、"性别"。
 * 通过 OneToMany 维护 DictItem 列表(cascade + remove)。
 */
@Entity
@Table(name = "t_dict_type")
public class DictType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 类型编码(唯一): user_status、gender */
    @Column(name = "type_code", nullable = false, unique = true, length = 50)
    private String typeCode;

    @Column(name = "type_name", nullable = false, length = 50)
    private String typeName;

    @Column(length = 200)
    private String description;

    /** 1=启用,0=禁用 */
    @Column(nullable = false)
    private Integer status = 1;

    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /** 该类型下的所有字典项(级联删除) */
    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DictItem> items = new ArrayList<>();

    public DictType() {}

    public DictType(String typeCode, String typeName, String description) {
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.description = description;
    }

    @jakarta.persistence.PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createTime = now;
        this.updateTime = now;
        if (this.status == null) this.status = 1;
    }

    @jakarta.persistence.PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }

    // ============== Getter / Setter ==============

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

    public List<DictItem> getItems() { return items; }
    public void setItems(List<DictItem> items) { this.items = items; }
}