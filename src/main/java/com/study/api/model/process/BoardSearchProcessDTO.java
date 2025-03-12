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
    private int searchDataCount;
    private int offset;
    private int pageSize;
    private int nowPage;
    private int lastPage;

    /* todo.
        nowPage가 1보다 작으면 offer 셋팅 시 음수가 되기 때문에 list 조회 시 쿼리 오류 발생.
        만약 front에서 nowPage 값으로 0을 보냈다면
        오류를 반환? or 1로 셋팅하여 페이지 조회?
    * */
    public void setNowPage(int nowPage) {
        if (nowPage < 1) {
            nowPage = 1;
        }
        this.nowPage = nowPage;
    }

    // 현재 페이지와 페이지 크기로 offset 셋팅
    public void setOffsetByNowPageAndPageSize(int nowPage, int pageSize) {
        if (nowPage < 1) {
            nowPage = 1;
        }
        this.offset = (nowPage - 1) * pageSize;
    }

    // 검색 적용한 데이터 수와 페이지 크기로 lastPage 셋팅
    public void setLastPageBySearchDataCountAndPageSize(int searchDataCount, int pageSize) {
        this.lastPage =(searchDataCount % pageSize == 0) ? searchDataCount / pageSize : searchDataCount / pageSize + 1;
    }
}
