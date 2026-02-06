package com.bank.recommendationservice.model.customer;

import java.util.List;
import java.time.LocalDate;
import java.math.BigDecimal;

public class CustomerProfileResponse {

    private Long customerId;
    private LocalDate dob;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpenses;
    private Integer riskScore;
    private String lifestyle;
    private String city;
    private String occupation;
    private List<Dependent> dependents;
    private List<Policy> policies;

    // getters and setters

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public BigDecimal getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setMonthlyExpenses(BigDecimal monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public String getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(String lifestyle) {
        this.lifestyle = lifestyle;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public List<Dependent> getDependents() {
        return dependents;
    }

    public void setDependents(List<Dependent> dependents) {
        this.dependents = dependents;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }

    // inner classes

    public static class Dependent {
        private String type;
        private Boolean financial;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Boolean isFinancial() {
            return financial;
        }

        public void setFinancial(Boolean financial) {
            this.financial = financial;
        }
    }

    public static class Policy {
        private String productType;
        private BigDecimal coverageAmount;

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public BigDecimal getCoverageAmount() {
            return coverageAmount;
        }

        public void setCoverageAmount(BigDecimal coverageAmount) {
            this.coverageAmount = coverageAmount;
        }
    }
}
