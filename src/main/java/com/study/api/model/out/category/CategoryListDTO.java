package com.study.api.model.out.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryListDTO {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;
}
