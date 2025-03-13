package com.study.api.model.out;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryListOutDTO(@JsonProperty("id") String id,
                                 @JsonProperty("name") String name) {
}
