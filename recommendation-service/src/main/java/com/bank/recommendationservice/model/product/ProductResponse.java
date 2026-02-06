package com.bank.recommendationservice.model.product;

public class ProductResponse {

    private Long productId;
    private String productName;
    private String productType;
    private Integer productSequence;
    private String description;
    private Integer minAge;
    private Integer maxAge;
    private Integer minIncome;
    private Integer maxRiskScore;
    private Integer maxDependents;
    private String allowedLifestyle;
    private String supportedOccupationRisk;
    private Integer monthlyPremium;
    private Integer coverageAmount;
    private Integer policyTermYears;
    private String targetFamilyType;
    private String productCode;
    private Boolean recommendedForFirstTimeBuyer;
    private Boolean isActive;

    // Getters & Setters (generate using IntelliJ)

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }

    public Integer getProductSequence() { return productSequence; }
    public void setProductSequence(Integer productSequence) { this.productSequence = productSequence; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getMinAge() { return minAge; }
    public void setMinAge(Integer minAge) { this.minAge = minAge; }

    public Integer getMaxAge() { return maxAge; }
    public void setMaxAge(Integer maxAge) { this.maxAge = maxAge; }

    public Integer getMinIncome() { return minIncome; }
    public void setMinIncome(Integer minIncome) { this.minIncome = minIncome; }

    public Integer getMaxRiskScore() { return maxRiskScore; }
    public void setMaxRiskScore(Integer maxRiskScore) { this.maxRiskScore = maxRiskScore; }

    public Integer getMaxDependents() { return maxDependents; }
    public void setMaxDependents(Integer maxDependents) { this.maxDependents = maxDependents; }

    public String getAllowedLifestyle() { return allowedLifestyle; }
    public void setAllowedLifestyle(String allowedLifestyle) { this.allowedLifestyle = allowedLifestyle; }

    public String getSupportedOccupationRisk() { return supportedOccupationRisk; }
    public void setSupportedOccupationRisk(String supportedOccupationRisk) { this.supportedOccupationRisk = supportedOccupationRisk; }

    public Integer getMonthlyPremium() { return monthlyPremium; }
    public void setMonthlyPremium(Integer monthlyPremium) { this.monthlyPremium = monthlyPremium; }

    public Integer getCoverageAmount() { return coverageAmount; }
    public void setCoverageAmount(Integer coverageAmount) { this.coverageAmount = coverageAmount; }

    public Integer getPolicyTermYears() { return policyTermYears; }
    public void setPolicyTermYears(Integer policyTermYears) { this.policyTermYears = policyTermYears; }

    public String getTargetFamilyType() { return targetFamilyType; }
    public void setTargetFamilyType(String targetFamilyType) { this.targetFamilyType = targetFamilyType; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public Boolean getRecommendedForFirstTimeBuyer() { return recommendedForFirstTimeBuyer; }
    public void setRecommendedForFirstTimeBuyer(Boolean recommendedForFirstTimeBuyer) { this.recommendedForFirstTimeBuyer = recommendedForFirstTimeBuyer; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean active) { isActive = active; }
}
