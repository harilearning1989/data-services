package com.web.demo.records;

import java.util.List;

public record Cart(
        int id,
        List<CartProduct> products,
        double total,
        double discountedTotal,
        int userId,
        int totalProducts,
        int totalQuantity
) {}
