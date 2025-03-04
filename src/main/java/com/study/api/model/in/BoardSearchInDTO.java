package com.study.api.model.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BoardSearchInDTO {
    // 검색조건
    @JsonProperty("search_register_date_start")
    private String searchRegisterDateStart;

    @JsonProperty("search_register_date_end")
    private String searchRegisterDateEnd;

    @JsonProperty("search_category")
    private String searchCategory;

    @JsonProperty("search_word")
    private String searchWord;

    // 현재 페이지
    @JsonProperty("now_page")
    private int nowPage = 1;
}
