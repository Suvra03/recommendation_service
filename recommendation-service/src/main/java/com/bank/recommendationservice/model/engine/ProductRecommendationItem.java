package com.bank.recommendationservice.model.engine;

import java.util.List;

public class ProductRecommendationItem {

    private Long productId;
    private String productName;
    private String productType;
    private Integer coverageAmount;
    private Integer durationYears;
    private Integer monthlyPremium;
    private String familyType;

    private boolean isRecommended;
    private boolean isTopUp;

    private List<String> reasons;

    // getters & setters

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }

    public Integer getCoverageAmount() { return coverageAmount; }
    public void setCoverageAmount(Integer coverageAmount) { this.coverageAmount = coverageAmount; }

    public Integer getDurationYears() { return durationYears; }
    public void setDurationYears(Integer durationYears) { this.durationYears = durationYears; }

    public Integer getMonthlyPremium() { return monthlyPremium; }
    public void setMonthlyPremium(Integer monthlyPremium) { this.monthlyPremium = monthlyPremium; }

    public String getFamilyType() { return familyType; }
    public void setFamilyType(String familyType) { this.familyType = familyType; }

    public boolean isRecommended() { return isRecommended; }
    public void setRecommended(boolean recommended) { isRecommended = recommended; }

    public boolean isTopUp() { return isTopUp; }
    public void setTopUp(boolean topUp) { isTopUp = topUp; }

    public List<String> getReasons() { return reasons; }
    public void setReasons(List<String> reasons) { this.reasons = reasons; }
}
