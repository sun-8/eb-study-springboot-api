package com.study.api.model.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.api.model.out.board.BoardListDTO;

import java.util.List;

public record BoardSearchOutDTO (@JsonProperty("now_page") int nowPage,
                                 @JsonProperty("search_data_count") int searchDataCount,
                                 @JsonProperty("first_page") int firstPage, // 기본값 1
                                 @JsonProperty("last_page") int lastPage,
                                 @JsonProperty("board_list") List<BoardListDTO> boardList) {
}
