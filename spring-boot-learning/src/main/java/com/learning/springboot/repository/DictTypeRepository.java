package com.learning.springboot.repository;

import com.learning.springboot.entity.DictType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 数据字典类型 Repository
 */
@Repository
public interface DictTypeRepository extends JpaRepository<DictType, Long> {

    Optional<DictType> findByTypeCode(String typeCode);

    boolean existsByTypeCode(String typeCode);

    List<DictType> findByStatus(Integer status);
}