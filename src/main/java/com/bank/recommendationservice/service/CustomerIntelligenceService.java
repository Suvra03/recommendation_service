package com.bank.recommendationservice.service;

import com.bank.recommendationservice.model.customer.CustomerProfileResponse;
import com.bank.recommendationservice.model.engine.CustomerIntelligenceProfile;
import com.bank.recommendationservice.model.engine.FamilyProfile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerIntelligenceService {

    public CustomerIntelligenceProfile buildProfile(CustomerProfileResponse customer) {
        System.out.println("Processing Customer ID: " + customer.getCustomerId());
        try {
            CustomerIntelligenceProfile profile = new CustomerIntelligenceProfile();

            // 1️⃣ Age
            int age = calculateAge(customer.getDob());
            profile.setAge(age);
            profile.setAgeBand(resolveAgeBand(age));

            // 2️⃣ Savings & Affordability
            // 2️⃣ Savings & Affordability
            BigDecimal income = customer.getMonthlyIncome() != null ? customer.getMonthlyIncome() : BigDecimal.ZERO;
            BigDecimal expenses = customer.getMonthlyExpenses() != null ? customer.getMonthlyExpenses()
                    : BigDecimal.ZERO;

            BigDecimal savings = income.subtract(expenses);
            profile.setMonthlySavings(savings.intValue());

            BigDecimal maxPremium = savings.multiply(BigDecimal.valueOf(0.3));
            profile.setMaxAffordablePremium(maxPremium.intValue());

            // Financial Stress check (avoid divide by zero)
            if (income.compareTo(BigDecimal.ZERO) == 0) {
                profile.setFinancialStress(true);
            } else {
                double ratio = savings.doubleValue() / income.doubleValue();
                profile.setFinancialStress(ratio < 0.2);
            }

            // 3️⃣ Risk Tier
            int riskScore = customer.getRiskScore() != null ? customer.getRiskScore() : 0;
            profile.setRiskTier(resolveRiskTier(riskScore));

            // 4️⃣ Family Profile
            profile.setFamilyProfile(buildFamilyProfile(customer));

            // 5️⃣ Existing Coverage
            Map<String, Integer> existingCoverage = aggregateCoverage(customer);
            profile.setExistingCoverage(existingCoverage);

            // 6️⃣ Coverage Gap
            profile.setCoverageGap(calculateCoverageGap(customer, existingCoverage));

            // 7️⃣ First Time Buyer
            profile.setFirstTimeBuyer(customer.getPolicies().isEmpty());

            profile.setCity(customer.getCity());
            profile.setLifestyle(customer.getLifestyle());

            return profile;
        } catch (Exception e) {
            System.err.println("ERROR building profile: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // ---------------- Helper Methods ----------------

    private int calculateAge(LocalDate dob) {
        if (dob == null)
            return 0;
        return Period.between(dob, LocalDate.now()).getYears();
    }

    private String resolveAgeBand(int age) {
        if (age <= 25)
            return "Young Adult";
        if (age <= 40)
            return "Working Professional";
        if (age <= 55)
            return "Family Builder";
        return "Senior";
    }

    private String resolveRiskTier(int score) {
        if (score <= 3)
            return "LOW";
        if (score <= 6)
            return "MEDIUM";
        return "HIGH";
    }

    private FamilyProfile buildFamilyProfile(CustomerProfileResponse customer) {
        FamilyProfile fp = new FamilyProfile();

        if (customer.getDependents() != null) {
            customer.getDependents().forEach(dep -> {
                if ("SPOUSE".equals(dep.getType()))
                    fp.setHasSpouse(true);
                if ("CHILD".equals(dep.getType()))
                    fp.setHasChildren(true);
                // Safe check for Boolean
                if ("PARENT".equals(dep.getType()) && Boolean.TRUE.equals(dep.isFinancial()))
                    fp.setHasDependentParents(true);
            });
        }

        return fp;
    }

    private Map<String, Integer> aggregateCoverage(CustomerProfileResponse customer) {
        Map<String, Integer> coverage = new HashMap<>();
        coverage.put("LIFE", 0);
        coverage.put("HEALTH", 0);
        coverage.put("ASSET", 0);

        if (customer.getPolicies() != null) {
            customer.getPolicies().forEach(policy -> {
                if (policy.getCoverageAmount() != null) {
                    coverage.merge(policy.getProductType(),
                            policy.getCoverageAmount().intValue(),
                            Integer::sum);
                }
            });
        }

        return coverage;
    }

    private Map<String, Integer> calculateCoverageGap(
            CustomerProfileResponse customer,
            Map<String, Integer> existing) {

        Map<String, Integer> gap = new HashMap<>();

        BigDecimal income = customer.getMonthlyIncome() != null ? customer.getMonthlyIncome() : BigDecimal.ZERO;
        int annualIncome = income.intValue() * 12;

        int lifeTarget = annualIncome * 10;
        int healthTarget = "Tier1".equalsIgnoreCase(customer.getCity()) ? 500000 : 300000;

        gap.put("LIFE", Math.max(0, lifeTarget - existing.get("LIFE")));
        gap.put("HEALTH", Math.max(0, healthTarget - existing.get("HEALTH")));
        gap.put("ASSET", 0);

        return gap;
    }
}
