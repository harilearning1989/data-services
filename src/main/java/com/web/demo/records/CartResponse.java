package com.web.demo.records;

import java.util.List;

public record CartResponse(List<Cart> carts,
                           int total,
                           int skip,
                           int limit) {
}
