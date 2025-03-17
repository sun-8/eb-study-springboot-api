package com.study.api.service;

import com.study.api.mapper.CategoryMapper;
import com.study.api.model.out.CategoryListOutDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 카테고리 모든 데이터 목록 조회
     * @return categoryList
     */
    public List<CategoryListOutDTO> getCategoryAllList() throws Exception {
        try {
            List<CategoryListOutDTO> categoryList = categoryMapper.getCategoryAllList();

            return categoryList;
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new Exception();
        }
    }
}
