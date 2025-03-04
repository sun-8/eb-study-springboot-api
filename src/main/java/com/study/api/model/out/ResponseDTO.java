package com.study.api.model.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseDTO<T> {
    @JsonProperty("response_code")
    private String responseCode = "9999";

    @JsonProperty("response_message")
    private String responseMessage = "";

    @JsonProperty("data")
    private T data;
}
