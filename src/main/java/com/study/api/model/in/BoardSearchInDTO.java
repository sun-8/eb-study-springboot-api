package com.study.api.model.in;

public record BoardSearchInDTO(String searchRegisterDateStart,
                               String searchRegisterDateEnd,
                               String searchCategory,
                               String searchWord,
                               int nowPage) {
}
