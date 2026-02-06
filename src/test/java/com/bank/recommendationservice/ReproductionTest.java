package com.bank.recommendationservice;

import com.bank.recommendationservice.model.customer.CustomerProfileResponse;
import com.bank.recommendationservice.model.engine.CustomerIntelligenceProfile;
import com.bank.recommendationservice.service.CustomerIntelligenceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

public class ReproductionTest {

    @Test
    public void testFullFlow() {
        System.out.println("--- START REPRODUCTION ---");
        try {
            // 1. Simulate DTO Construction
            CustomerProfileResponse dto = new CustomerProfileResponse();
            dto.setCustomerId(1001L);
            dto.setDob(LocalDate.of(1990, 1, 1));
            dto.setMonthlyIncome(new BigDecimal("150000"));
            dto.setMonthlyExpenses(new BigDecimal("50000"));
            dto.setRiskScore(null); // Simulate missing risk score
            dto.setCity("Tier1");
            dto.setDependents(Collections.emptyList());
            dto.setPolicies(Collections.emptyList());

            System.out.println("DTO Created: " + dto);

            // 2. Run Service Logic
            CustomerIntelligenceService service = new CustomerIntelligenceService();
            CustomerIntelligenceProfile profile = service.buildProfile(dto);

            System.out.println("Profile Built: " + profile);

        } catch (Exception e) {
            System.err.println("--- CRASH REPRODUCED ---");
            e.printStackTrace();
        }
        System.out.println("--- END REPRODUCTION ---");
    }

    @Test
    public void testJsonDeserialization() {
        System.out.println("--- START JSON TEST ---");
        try {
            // 2. Simulate JSON from Customer Service
            String json = "{\n" +
                    "    \"customerId\": 1001,\n" +
                    "    \"dob\": [1990, 1, 1],\n" +
                    "    \"monthlyIncome\": 150000,\n" +
                    "    \"monthlyExpenses\": 50000,\n" +
                    "    \"riskScore\": null,\n" +
                    "    \"city\": \"Tier1\",\n" +
                    "    \"dependents\": [],\n" +
                    "    \"policies\": []\n" +
                    "}";

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            CustomerProfileResponse dto = mapper.readValue(json, CustomerProfileResponse.class);
            System.out.println("Deserialized DTO: " + dto.getCustomerId());

        } catch (Exception e) {
            System.err.println("--- JSON CRASH REPRODUCED ---");
            e.printStackTrace();
        }
        System.out.println("--- END JSON TEST ---");
    }
}
