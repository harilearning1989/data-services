package com.web.demo.records;

public record Book(
        Long id,
        String title,
        String description,
        String excerpt,
        String publishDate
) {}

