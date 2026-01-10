package dev.rumetshofer.orderservice.repository;

import dev.rumetshofer.orderservice.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepository {

    private final Map<String, Order> orders = new ConcurrentHashMap<>();

    public List<Order> findAll() {
        return List.copyOf(orders.values());
    }

    public Optional<Order> findById(String id) {
        return Optional.ofNullable(orders.get(id));
    }

    public Order save(Order order) {
        orders.put(order.id(), order);
        return order;
    }
}
