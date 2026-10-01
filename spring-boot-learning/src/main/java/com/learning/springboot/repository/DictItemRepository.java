package com.learning.springboot.repository;

import com.learning.springboot.entity.DictItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据字典项 Repository
 */
@Repository
public interface DictItemRepository extends JpaRepository<DictItem, Long> {

    // 派生方法
    List<DictItem> findByTypeCodeOrderBySortAsc(String typeCode);

    List<DictItem> findByTypeCodeAndStatusOrderBySortAsc(String typeCode, Integer status);

    // JPQL: 删除某类型的所有项
    @Modifying
    @Query("DELETE FROM DictItem d WHERE d.typeCode = :typeCode")
    int deleteByTypeCode(@Param("typeCode") String typeCode);
}