package com.web.demo.records;

public record CartItem(int id, String title, double price, int quantity, double total, double discountPercentage,
                       double discountedPrice, String thumbnail) {
}
