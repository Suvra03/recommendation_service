package com.bank.recommendationservice.client;

import com.bank.recommendationservice.model.customer.CustomerProfileResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CustomerServiceClient {

    private final WebClient webClient;

    public CustomerServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public CustomerProfileResponse getCustomerProfile(Long customerId) {
        String url = "http://localhost:8081/api/customers/" + customerId + "/secondProfile";
        System.out.println("Calling URL: " + url);
        try {
            String json = webClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("Received JSON from Customer Service: " + json);

            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
            mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return mapper.readValue(json, CustomerProfileResponse.class);

        } catch (Exception e) {
            System.err.println("ERROR in CustomerServiceClient: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to get customer profile", e);
        }
    }
}
