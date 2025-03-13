package com.study.api.service;

import com.study.api.config.ResponseDTO;
import com.study.api.mapper.CategoryMapper;
import com.study.api.model.out.CategoryListOutDTO;
import com.study.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Service
public class CategoryService {
    Logger logger = Logger.getLogger(CategoryService.class.getName());

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 카테고리 모든 데이터 목록 조회
     * @return categoryList
     */
    public ResponseDTO<List<CategoryListOutDTO>> getCategoryAllList() {

        ResponseDTO<List<CategoryListOutDTO>> outDTO = new ResponseDTO<>();

        try {
            List<CategoryListOutDTO> categoryList = categoryMapper.getCategoryAllList();

            outDTO.setResponseCode(Message.SUCCESS_CODE_0000);
            outDTO.setResponseData(categoryList);

        } catch (Exception e) {
            log.info(e.getMessage());
            outDTO.setResponseCode(Message.ERROR_CODE_9999);
            outDTO.setResponseMessage(Message.ERROR_MESSAGE_9999);
        }

        return outDTO;
    }
}
