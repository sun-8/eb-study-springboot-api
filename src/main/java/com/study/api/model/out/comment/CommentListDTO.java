package com.study.api.model.out.comment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CommentListDTO(@JsonProperty("register_datetime") String registerDatetime,
                             @JsonProperty("comments") String comments) {
}
