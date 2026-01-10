package dev.rumetshofer.orderservice.model;

import java.time.Instant;
import java.util.List;

public record Order(
        String id,
        List<OrderItem> items,
        Instant createdAt
) {
}
