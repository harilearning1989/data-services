package com.web.demo.records;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "body",
        "postId",
        "likes",
        "user"
})
public record Comments(
        @JsonProperty("id")
        Integer id,
        @JsonProperty("body")
        String body,
        @JsonProperty("postId")
        Integer postId,
        @JsonProperty("likes")
        Integer likes,
        @JsonProperty("user")
        UserComments user
) {
}