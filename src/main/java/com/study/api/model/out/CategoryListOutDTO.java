package com.study.api.model.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryListOutDTO {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;
}
