package com.web.demo.records;

public record Authors(
        Long id,
        Long idBook,
        String firstName,
        String lastName
) {
}
