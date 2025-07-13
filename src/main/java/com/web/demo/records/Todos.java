package com.web.demo.records;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "todo",
        "completed",
        "userId"
})
public record Todos(
        @JsonProperty("id")
        Integer id,
        @JsonProperty("todo")
        String todo,
        @JsonProperty("completed")
        Boolean completed,
        @JsonProperty("userId")
        Integer userId
) {
}
