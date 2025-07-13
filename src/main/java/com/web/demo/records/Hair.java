package com.web.demo.records;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "color",
        "type"
})
public record Hair(@JsonProperty("color")
                   String color,
                   @JsonProperty("type")
                   String type) {
}
