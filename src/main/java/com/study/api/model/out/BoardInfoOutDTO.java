package com.study.api.model.out;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BoardInfoOutDTO(@JsonProperty("id") int id,
                              @JsonProperty("category_name") String category_name,
                              @JsonProperty("user_name") String userName,
                              @JsonProperty("title") String title,
                              @JsonProperty("contents") String contents,
                              @JsonProperty("views") int views,
                              @JsonProperty("file_id") List<String> fileId,
                              @JsonProperty("register_datetime") String registerDatetime,
                              @JsonProperty("modify_datetime") String modifyDatetime) {
}
