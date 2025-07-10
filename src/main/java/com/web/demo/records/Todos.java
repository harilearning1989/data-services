package com.web.demo.records;

public record Todos(
        Long userId,
        Long id,
        String title,
        boolean completed
) {
}
