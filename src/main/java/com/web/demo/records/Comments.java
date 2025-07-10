package com.web.demo.records;

public record Comments(
        Long postId,
        Long id,
        String name,
        String email,
        String body
) {
}