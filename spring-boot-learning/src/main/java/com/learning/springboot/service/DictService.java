package com.learning.springboot.service;

import com.learning.springboot.dto.DictItemDTO;
import com.learning.springboot.dto.DictTypeDTO;

import java.util.List;

/**
 * 字典服务接口
 */
public interface DictService {

    // 类型
    List<DictTypeDTO> listTypes();
    DictTypeDTO createType(String typeCode, String typeName, String description);
    DictTypeDTO updateType(Long id, String typeName, String description, Integer status);
    void deleteType(Long id);

    // 项
    List<DictItemDTO> listItemsByType(String typeCode);
    DictItemDTO createItem(Long typeId, String itemCode, String itemValue, Integer sort, String remark);
    DictItemDTO updateItem(Long id, String itemValue, Integer sort, Integer status, String remark);
    void deleteItem(Long id);
}