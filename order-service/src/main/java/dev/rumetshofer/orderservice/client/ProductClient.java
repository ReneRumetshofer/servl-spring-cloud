package dev.rumetshofer.orderservice.client;

import dev.rumetshofer.orderservice.dto.ProductResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class ProductClient {

    private static final String PRODUCT_SERVICE_URL = "http://product-service/products";

    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<ProductResponse> getProductById(String productId) {
        try {
            ProductResponse product = restTemplate.getForObject(
                    PRODUCT_SERVICE_URL + "/" + productId,
                    ProductResponse.class
            );
            return Optional.ofNullable(product);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
