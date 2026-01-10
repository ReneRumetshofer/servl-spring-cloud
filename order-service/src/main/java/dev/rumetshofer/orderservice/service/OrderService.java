package dev.rumetshofer.orderservice.service;

import dev.rumetshofer.orderservice.client.ProductClient;
import dev.rumetshofer.orderservice.dto.ProductResponse;
import dev.rumetshofer.orderservice.model.Order;
import dev.rumetshofer.orderservice.model.OrderItem;
import dev.rumetshofer.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public OrderService(OrderRepository orderRepository, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    public Order createOrder(List<String> productIds) {
        List<OrderItem> items = new ArrayList<>();

        for (String productId : productIds) {
            Optional<ProductResponse> product = productClient.getProductById(productId);
            String productName = product.map(ProductResponse::name).orElse("Unknown Product");
            items.add(new OrderItem(productId, productName));
        }

        Order order = new Order(
                UUID.randomUUID().toString(),
                items,
                Instant.now()
        );

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }
}
