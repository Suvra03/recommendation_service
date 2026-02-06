package com.bank.recommendationservice.client;

import com.bank.recommendationservice.model.product.ProductResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class ProductServiceClient {

    private final WebClient webClient;

    public ProductServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<ProductResponse> getProductsForType(String type) {
        String url = "http://localhost:8084/products/recommendation?type=" + type;
        System.out.println("Calling Product Service: " + url);
        try {
            return webClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToFlux(ProductResponse.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            System.err.println("ERROR calling Product Service: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Return empty list instead of crashing
        }
    }
}
