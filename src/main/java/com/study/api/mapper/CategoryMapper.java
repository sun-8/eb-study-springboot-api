package com.study.api.mapper;

import com.study.api.model.out.CategoryListOutDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 카테고리 목록 조회
     * @return List<CategoryEntity>
     */
    List<CategoryListOutDTO> getCategoryAllList();
}
