package com.bank.recommendationservice.controller;

import com.bank.recommendationservice.client.CustomerServiceClient;
import com.bank.recommendationservice.client.ProductServiceClient;
import com.bank.recommendationservice.model.customer.CustomerProfileResponse;
import com.bank.recommendationservice.model.engine.CustomerIntelligenceProfile;
import com.bank.recommendationservice.model.engine.ProductEvaluation;
import com.bank.recommendationservice.model.engine.ProductRecommendationItem;
import com.bank.recommendationservice.model.engine.RecommendationResponse;
import com.bank.recommendationservice.model.product.ProductResponse;
import com.bank.recommendationservice.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    // 🔹 All dependencies
    private final CustomerServiceClient customerServiceClient;
    private final ProductServiceClient productServiceClient;
    private final CustomerIntelligenceService customerIntelligenceService;
    private final NeedsRankingService needsRankingService;
    private final ProductEligibilityService productEligibilityService;
    private final ProductScoringService productScoringService;
    private final ExplanationService explanationService;

    // 🔹 ONE constructor, ALL dependencies injected
    public RecommendationController(CustomerServiceClient customerServiceClient,
            ProductServiceClient productServiceClient,
            CustomerIntelligenceService customerIntelligenceService,
            NeedsRankingService needsRankingService,
            ProductEligibilityService productEligibilityService,
            ProductScoringService productScoringService,
            ExplanationService explanationService) {

        this.customerServiceClient = customerServiceClient;
        this.productServiceClient = productServiceClient;
        this.customerIntelligenceService = customerIntelligenceService;
        this.needsRankingService = needsRankingService;
        this.productEligibilityService = productEligibilityService;
        this.productScoringService = productScoringService;
        this.explanationService = explanationService;

    }

    // ---------- Health Check ----------
    @GetMapping("/health")
    public String healthCheck() {
        return "Recommendation Service is UP";
    }

    // ---------- Test Customer API ----------
    @GetMapping("/test-customer/{id}")
    public CustomerProfileResponse testCustomer(@PathVariable Long id) {
        return customerServiceClient.getCustomerProfile(id);
    }

    // ---------- Test Product API ----------
    @GetMapping("/test-products/{type}")
    public List<ProductResponse> testProducts(@PathVariable String type) {
        return productServiceClient.getProductsForType(type);
    }

    // ---------- Phase A: Customer Intelligence ----------
    @GetMapping("/intelligence/{id}")
    public CustomerIntelligenceProfile buildIntelligence(@PathVariable Long id) {
        var customer = customerServiceClient.getCustomerProfile(id);
        return customerIntelligenceService.buildProfile(customer);
    }

    // ---------- Phase B: Needs Ranking ----------
    @GetMapping("/needs/{id}")
    public List<String> rankNeeds(@PathVariable Long id) {
        var customer = customerServiceClient.getCustomerProfile(id);
        var profile = customerIntelligenceService.buildProfile(customer);
        return needsRankingService.rankNeeds(profile);
    }

    // Phase C
    @GetMapping("/eligible-products/{id}")
    public List<ProductResponse> eligibleProducts(@PathVariable Long id) {

        var customer = customerServiceClient.getCustomerProfile(id);
        if (customer == null) {
            return List.of();
        }
        var profile = customerIntelligenceService.buildProfile(customer);

        var needs = needsRankingService.rankNeeds(profile);
        if (needs.isEmpty()) {
            return List.of();
        }
        var primaryNeed = needs.get(0); // top priority

        var products = productServiceClient.getProductsForType(primaryNeed);
        if (products == null || products.isEmpty()) {
            return List.of();
        }

        return productEligibilityService.filterEligibleProducts(products, profile);
    }

    @GetMapping("/score-products/{id}")
    public List<ProductEvaluation> scoreProducts(@PathVariable Long id) {

        var customer = customerServiceClient.getCustomerProfile(id);
        if (customer == null) {
            return List.of();
        }
        var profile = customerIntelligenceService.buildProfile(customer);

        var needs = needsRankingService.rankNeeds(profile);
        if (needs.isEmpty()) {
            return List.of();
        }
        var primaryNeed = needs.get(0);

        var products = productServiceClient.getProductsForType(primaryNeed);
        if (products == null || products.isEmpty()) {
            return List.of();
        }
        var eligible = productEligibilityService.filterEligibleProducts(products, profile);

        return eligible.stream()
                .map(p -> productScoringService.scoreProduct(p, profile))
                .sorted((a, b) -> b.getFinalScore() - a.getFinalScore())
                .toList();
    }

    @GetMapping("/recommend/{id}")
    public RecommendationResponse recommend(@PathVariable Long id) {
        try {
            var customer = customerServiceClient.getCustomerProfile(id);
            if (customer == null) {
                throw new RuntimeException("Customer not found for ID: " + id);
            }

            var profile = customerIntelligenceService.buildProfile(customer);

            var needs = needsRankingService.rankNeeds(profile);
            if (needs.isEmpty()) {
                RecommendationResponse response = new RecommendationResponse();
                response.setProducts(List.of());
                response.setStatus("NO_MATCH");
                response.setRuleVersion("1.0");
                return response;
            }
            var primaryNeed = needs.get(0);

            var products = productServiceClient.getProductsForType(primaryNeed);
            if (products == null || products.isEmpty()) {
                RecommendationResponse response = new RecommendationResponse();
                response.setProducts(List.of());
                response.setStatus("NO_MATCH - No products found for need: " + primaryNeed);
                response.setRuleVersion("1.0");
                return response;
            }

            var eligible = productEligibilityService.filterEligibleProducts(products, profile);
            if (eligible.isEmpty()) {
                RecommendationResponse response = new RecommendationResponse();
                response.setProducts(List.of());
                response.setStatus("NO_MATCH - No eligible products");
                response.setRuleVersion("1.0");
                return response;
            }

            var scored = eligible.stream()
                    .map(p -> productScoringService.scoreProduct(p, profile))
                    .sorted((a, b) -> b.getFinalScore() - a.getFinalScore())
                    .toList();

            if (scored.isEmpty()) {
                RecommendationResponse response = new RecommendationResponse();
                response.setProducts(List.of());
                response.setStatus("NO_MATCH - Scoring failed");
                response.setRuleVersion("1.0");
                return response;
            }

            ProductEvaluation topPick = scored.get(0);

            List<ProductRecommendationItem> responseProducts = scored.stream().map(ev -> {

                ProductRecommendationItem item = new ProductRecommendationItem();

                item.setProductId(ev.getProduct().getProductId());
                item.setProductName(ev.getProduct().getProductName());
                item.setProductType(ev.getProduct().getProductType());
                item.setCoverageAmount(ev.getProduct().getCoverageAmount());
                item.setDurationYears(ev.getProduct().getPolicyTermYears());
                item.setMonthlyPremium(ev.getProduct().getMonthlyPremium());
                item.setFamilyType(ev.getProduct().getTargetFamilyType());

                item.setTopUp("HEALTH".equals(ev.getProduct().getProductType())
                        && ev.getProduct().getCoverageAmount() < 500000);

                item.setRecommended(ev == topPick);
                item.setReasons(ev.getReasonCodes());

                return item;
            }).toList();

            RecommendationResponse response = new RecommendationResponse();
            response.setProducts(responseProducts);
            response.setRuleVersion("1.0");
            response.setStatus(responseProducts.isEmpty() ? "NO_MATCH" : "RECOMMENDED");

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            RecommendationResponse response = new RecommendationResponse();
            response.setProducts(List.of());
            response.setStatus("ERROR: " + e.getMessage() + " [" + e.getClass().getSimpleName() + "]");
            response.setRuleVersion("1.0");
            return response;
        }
    }

}
