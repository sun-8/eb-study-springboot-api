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
}
