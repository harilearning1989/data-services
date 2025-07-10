package com.web.demo.records;

public record AdminRecord(
        int adminId,
        String firstName,
        String lastName,
        String gender,
        String specialty,
        String email,
        String phone,
        AddressRecord address
) {
}
