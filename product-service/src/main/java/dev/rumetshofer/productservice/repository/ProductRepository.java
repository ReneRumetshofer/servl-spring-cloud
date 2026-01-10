package dev.rumetshofer.productservice.repository;

import dev.rumetshofer.productservice.model.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProductRepository {

    private final Map<String, Product> products = new ConcurrentHashMap<>();

    public ProductRepository() {
        initializeProducts();
    }

    private void initializeProducts() {
        save(new Product("1", "Clean Code", "A Handbook of Agile Software Craftsmanship by Robert C. Martin", new BigDecimal("34.99")));
        save(new Product("2", "Design Patterns", "Elements of Reusable Object-Oriented Software by Gang of Four", new BigDecimal("49.99")));
        save(new Product("3", "The Pragmatic Programmer", "Your Journey to Mastery by David Thomas and Andrew Hunt", new BigDecimal("44.99")));
        save(new Product("4", "Refactoring", "Improving the Design of Existing Code by Martin Fowler", new BigDecimal("47.99")));
        save(new Product("5", "Domain-Driven Design", "Tackling Complexity in the Heart of Software by Eric Evans", new BigDecimal("59.99")));
    }

    public List<Product> findAll() {
        return List.copyOf(products.values());
    }

    public Optional<Product> findById(String id) {
        return Optional.ofNullable(products.get(id));
    }

    public Product save(Product product) {
        products.put(product.id(), product);
        return product;
    }

    public void deleteById(String id) {
        products.remove(id);
    }

    public boolean existsById(String id) {
        return products.containsKey(id);
    }
}
