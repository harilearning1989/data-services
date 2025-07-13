package com.web.demo.records;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "firstName",
        "lastName",
        "maidenName",
        "age",
        "gender",
        "email",
        "phone",
        "username",
        "password",
        "birthDate",
        "image",
        "bloodGroup",
        "height",
        "weight",
        "eyeColor",
        "hair",
        "ip",
        "address",
        "macAddress",
        "university",
        "bank",
        "company",
        "ein",
        "ssn",
        "userAgent",
        "crypto",
        "role"
})
public record Users(
        @JsonProperty("id")
        Integer id,
        @JsonProperty("firstName")
        String firstName,
        @JsonProperty("lastName")
        String lastName,
        @JsonProperty("maidenName")
        String maidenName,
        @JsonProperty("age")
        Integer age,
        @JsonProperty("gender")
        String gender,
        @JsonProperty("email")
        String email,
        @JsonProperty("phone")
        String phone,
        @JsonProperty("username")
        String username,
        @JsonProperty("password")
        String password,
        @JsonProperty("birthDate")
        String birthDate,
        @JsonProperty("image")
        String image,
        @JsonProperty("bloodGroup")
        String bloodGroup,
        @JsonProperty("height")
        Double height,
        @JsonProperty("weight")
        Double weight,
        @JsonProperty("eyeColor")
        String eyeColor,
        @JsonProperty("hair")
        Hair hair,
        @JsonProperty("ip")
        String ip,
        @JsonProperty("address")
        Address address,
        @JsonProperty("macAddress")
        String macAddress,
        @JsonProperty("university")
        String university,
        //@JsonProperty("bank")
        //Bank bank,
        //@JsonProperty("company")
        //Company company,
        @JsonProperty("ein")
        String ein,
        @JsonProperty("ssn")
        String ssn,
        @JsonProperty("userAgent")
        String userAgent,
        //@JsonProperty("crypto")
        //Crypto crypto,
        @JsonProperty("role")
        String role
        ) {
}
