package com.web.demo.records;

public record Photos(
        Long albumId,
        Long id,
        String title,
        String url,
        String thumbnailUrl
) {
}
