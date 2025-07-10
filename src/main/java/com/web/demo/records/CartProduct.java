package com.web.demo.records;

public record CartProduct(int id,
                          String title,
                          double price,
                          int quantity,
                          double total,
                          double discountPercentage,
                          double discountedTotal,
                          String thumbnail) {
}
