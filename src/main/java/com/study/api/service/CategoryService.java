package com.study.api.service;

import com.study.api.mapper.CategoryMapper;
import com.study.api.model.out.CategoryListOutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class CategoryService {
    Logger logger = Logger.getLogger(CategoryService.class.getName());

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 카테고리 모든 데이터 목록 조회
     * @return categoryList
     */
    public List<CategoryListOutDTO> getCategoryAllList() {

        List<CategoryListOutDTO> categoryList = categoryMapper.getCategoryAllList();

        return categoryList;
    }
}
