package com.study.api.model.out.board;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BoardListDTO {
    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("file_yes_or_no")
    private String fileYesOrNo;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("title")
    private String title;

    @JsonProperty("views")
    private int views;

    @JsonProperty("register_datetime")
    private String registerDatetime;

    @JsonProperty("modify_datetime")
    private String modifyDatetime;
}
