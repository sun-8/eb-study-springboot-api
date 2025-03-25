package com.study.api.common.code;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
    @JsonProperty("field_name")
    private String fieldName;

    @JsonProperty("error_message")
    private String errorMessage;
}
