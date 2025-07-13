package com.web.demo.records;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "username",
        "fullName"
})
public record UserComments(@JsonProperty("id")
                           Integer id,
                           @JsonProperty("username")
                           String username,
                           @JsonProperty("fullName")
                           String fullName) {
}
