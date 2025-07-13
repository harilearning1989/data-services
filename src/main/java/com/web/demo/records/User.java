package com.web.demo.records;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "users",
        "total",
        "skip",
        "limit"
})
public record User(@JsonProperty("users")
                   List<Users> users,
                   @JsonProperty("total")
                   Integer total,
                   @JsonProperty("skip")
                   Integer skip,
                   @JsonProperty("limit")
                   Integer limit) {
}
