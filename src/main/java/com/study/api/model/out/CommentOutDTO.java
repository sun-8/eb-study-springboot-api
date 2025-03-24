package com.study.api.model.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.api.model.out.comment.CommentListDTO;

import java.util.List;

public record CommentOutDTO(@JsonProperty("comment_list") List<CommentListDTO> commentList) {
}
