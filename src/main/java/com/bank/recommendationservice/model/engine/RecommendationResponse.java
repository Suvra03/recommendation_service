package com.bank.recommendationservice.model.engine;

import java.util.List;

public class RecommendationResponse {

    private List<ProductRecommendationItem> products;
    private String ruleVersion;
    private String status;

    public List<ProductRecommendationItem> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRecommendationItem> products) {
        this.products = products;
    }

    public String getRuleVersion() {
        return ruleVersion;
    }

    public void setRuleVersion(String ruleVersion) {
        this.ruleVersion = ruleVersion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
