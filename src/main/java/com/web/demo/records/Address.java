package com.web.demo.records;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "address",
        "city",
        "state",
        "stateCode",
        "postalCode",
        "coordinates",
        "country"
})
public record Address(
        @JsonProperty("address")
        String address,
        @JsonProperty("city")
        String city,
        @JsonProperty("state")
        String state,
        @JsonProperty("stateCode")
        String stateCode,
        @JsonProperty("postalCode")
        String postalCode,
        //@JsonProperty("coordinates")
        //Coordinates coordinates,
        @JsonProperty("country")
        String country
) {
}
