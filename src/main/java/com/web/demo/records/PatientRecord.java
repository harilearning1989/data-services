package com.web.demo.records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PatientRecord(
        int patientId,
        String firstName,
        String lastName,
        String dateOfBirth,
        String gender,
        AddressRecord address,
        String email,
        String phone

) {

}
