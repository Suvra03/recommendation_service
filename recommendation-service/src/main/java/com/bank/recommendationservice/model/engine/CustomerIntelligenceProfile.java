package com.bank.recommendationservice.model.engine;

import java.util.Map;

public class CustomerIntelligenceProfile {

    private int age;
    private String ageBand;

    private int monthlySavings;
    private int maxAffordablePremium;
    private boolean financialStress;

    private String riskTier;

    private FamilyProfile familyProfile;

    private Map<String, Integer> existingCoverage;
    private Map<String, Integer> coverageGap;

    private String city;
    private String lifestyle;
    private boolean firstTimeBuyer;

    // getters & setters

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getAgeBand() { return ageBand; }
    public void setAgeBand(String ageBand) { this.ageBand = ageBand; }

    public int getMonthlySavings() { return monthlySavings; }
    public void setMonthlySavings(int monthlySavings) { this.monthlySavings = monthlySavings; }

    public int getMaxAffordablePremium() { return maxAffordablePremium; }
    public void setMaxAffordablePremium(int maxAffordablePremium) {
        this.maxAffordablePremium = maxAffordablePremium;
    }

    public boolean isFinancialStress() { return financialStress; }
    public void setFinancialStress(boolean financialStress) {
        this.financialStress = financialStress;
    }

    public String getRiskTier() { return riskTier; }
    public void setRiskTier(String riskTier) { this.riskTier = riskTier; }

    public FamilyProfile getFamilyProfile() { return familyProfile; }
    public void setFamilyProfile(FamilyProfile familyProfile) {
        this.familyProfile = familyProfile;
    }

    public Map<String, Integer> getExistingCoverage() {
        return existingCoverage;
    }

    public void setExistingCoverage(Map<String, Integer> existingCoverage) {
        this.existingCoverage = existingCoverage;
    }

    public Map<String, Integer> getCoverageGap() {
        return coverageGap;
    }

    public void setCoverageGap(Map<String, Integer> coverageGap) {
        this.coverageGap = coverageGap;
    }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getLifestyle() { return lifestyle; }
    public void setLifestyle(String lifestyle) { this.lifestyle = lifestyle; }

    public boolean isFirstTimeBuyer() { return firstTimeBuyer; }
    public void setFirstTimeBuyer(boolean firstTimeBuyer) {
        this.firstTimeBuyer = firstTimeBuyer;
    }
}
