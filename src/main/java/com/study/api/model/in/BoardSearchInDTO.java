package com.study.api.model.in;

import lombok.Data;

@Data
public class BoardSearchInDTO {
    // 검색조건
    private String searchRegisterDateStart;

    private String searchRegisterDateEnd;

    private String searchCategory;

    private String searchWord;

    // 현재 페이지
    private int nowPage = 1;
}
