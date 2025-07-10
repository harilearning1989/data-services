package com.web.demo.records;

import java.math.BigDecimal;

public record Product(
        Long id,
        String title,
        String description,
        BigDecimal price,
        String category,
        String image
) {}

