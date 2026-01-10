package dev.rumetshofer.orderservice.dto;

import java.util.List;

public record CreateOrderRequest(
        List<String> productIds
) {
}
