package com.web.demo.records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DoctorRecord(
        int doctorId,
        String firstName,
        String lastName,
        String gender,
        String specialty,
        String email,
        String phone,
        AddressRecord address
) {

}
