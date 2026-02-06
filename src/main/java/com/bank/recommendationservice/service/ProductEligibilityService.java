package com.bank.recommendationservice.service;

import com.bank.recommendationservice.model.engine.CustomerIntelligenceProfile;
import com.bank.recommendationservice.model.product.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductEligibilityService {

    public List<ProductResponse> filterEligibleProducts(
            List<ProductResponse> products,
            CustomerIntelligenceProfile profile) {

        return products.stream()
                .filter(p -> Boolean.TRUE.equals(p.getIsActive()))
                .filter(p -> isAgeEligible(p, profile))
                .filter(p -> isIncomeEligible(p, profile))
                .filter(p -> isRiskEligible(p, profile))
                .filter(p -> isDependentsEligible(p, profile))
                .filter(p -> isLifestyleEligible(p, profile))
                .filter(p -> isAffordable(p, profile))
                .toList();
    }

    // ----------------- Rule Checks -----------------

    private boolean isAgeEligible(ProductResponse p, CustomerIntelligenceProfile profile) {
        return profile.getAge() >= p.getMinAge()
                && profile.getAge() <= p.getMaxAge();
    }

    private boolean isIncomeEligible(ProductResponse p, CustomerIntelligenceProfile profile) {
        return profile.getMonthlySavings() >= 0
                && profile.getMaxAffordablePremium() > 0
                && profile.getMonthlySavings() >= p.getMinIncome();
    }

    private boolean isRiskEligible(ProductResponse p, CustomerIntelligenceProfile profile) {
        if ("HIGH".equals(profile.getRiskTier()))
            return p.getMaxRiskScore() >= 7;
        if ("MEDIUM".equals(profile.getRiskTier()))
            return p.getMaxRiskScore() >= 4;
        return true;
    }

    private boolean isDependentsEligible(ProductResponse p, CustomerIntelligenceProfile profile) {
        int dependents = 0;
        if (profile.getFamilyProfile().isHasSpouse()) dependents++;
        if (profile.getFamilyProfile().isHasChildren()) dependents++;

        return dependents <= p.getMaxDependents();
    }

    private boolean isLifestyleEligible(ProductResponse p, CustomerIntelligenceProfile profile) {
        return "ANY".equalsIgnoreCase(p.getAllowedLifestyle())
                || p.getAllowedLifestyle().equalsIgnoreCase(profile.getLifestyle());
    }

    private boolean isAffordable(ProductResponse p, CustomerIntelligenceProfile profile) {
        return p.getMonthlyPremium() <= profile.getMaxAffordablePremium();
    }
}
