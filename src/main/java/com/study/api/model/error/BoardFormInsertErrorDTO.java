package com.study.api.model.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BoardFormInsertErrorDTO {
    @JsonProperty("category_id_error_message")
    private String categoryIdErrorMessage;

    @JsonProperty("user_name_error_message")
    private String userNameErrorMessage;

    @JsonProperty("password_error_message")
    private String passwordErrorMessage;

    @JsonProperty("password_check_error_message")
    private String passwordCheckErrorMessage;

    @JsonProperty("title_error_message")
    private String titleErrorMessage;

    @JsonProperty("content_error_message")
    private String contentsErrorMessage;
}
