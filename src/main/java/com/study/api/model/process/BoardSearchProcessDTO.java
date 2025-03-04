package com.study.api.model.process;

import lombok.Data;

@Data
public class BoardSearchProcessDTO {
    // 검색 조건
    private String searchRegisterDateStart;
    private String searchRegisterDateEnd;
    private String searchCategory;
    private String searchWord;

    // 페이징
    private int offset;
    private int pageSize;

    // 현재 페이지와 페이지 크기로 offset 셋팅
    public void setOffsetByNowPageAndPageSize(int nowPage, int pageSize) {
        this.offset = (nowPage - 1) * pageSize;
    }
}
