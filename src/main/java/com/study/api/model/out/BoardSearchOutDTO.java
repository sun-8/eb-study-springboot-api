package com.study.api.model.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.api.model.out.board.BoardListDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BoardSearchOutDTO {

    // 페이지네이션
    @JsonProperty("now_page")
    private int nowPage;

    @JsonProperty("search_data_count")
    private int searchDataCount;

    @JsonProperty("first_page")
    private int firstPage = 1;

    @JsonProperty("last_page")
    private int lastPage;

    // 게시판 목록
    @JsonProperty("board_list")
    private List<BoardListDTO> boardList;

    // 검색 적용한 데이터 수와 페이지 크기로 lastPage 셋팅
    public void setLastPageBySearchDataCountAndPageSize(int searchDataCount, int pageSize) {
        this.lastPage =(searchDataCount % pageSize == 0) ? searchDataCount / pageSize : searchDataCount / pageSize + 1;
    }
}
