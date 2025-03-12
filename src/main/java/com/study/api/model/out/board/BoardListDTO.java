package com.study.api.model.out.board;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BoardListDTO (@JsonProperty("category_name") String categoryName,
                            @JsonProperty("file_yes_or_no") String fileYesOrNo,
                            @JsonProperty("user_name") String userName,
                            @JsonProperty("title") String title,
                            @JsonProperty("views") int views,
                            @JsonProperty("register_datetime") String registerDatetime,
                            @JsonProperty("modify_datetime") String modifyDatetime) {
}
