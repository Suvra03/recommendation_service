package com.bank.recommendationservice.service;

import com.bank.recommendationservice.model.engine.CustomerIntelligenceProfile;
import com.bank.recommendationservice.model.engine.ProductEvaluation;
import com.bank.recommendationservice.model.product.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductScoringService {

    public ProductEvaluation scoreProduct(ProductResponse product,
                                          CustomerIntelligenceProfile profile) {

        int protection = scoreProtection(product, profile);
        int affordability = scoreAffordability(product, profile);
        int risk = scoreRisk(product, profile);
        int context = scoreContext(product, profile);

        int finalScore =
                (int) (protection * 0.40
                        + affordability * 0.30
                        + risk * 0.20
                        + context * 0.10);

        ProductEvaluation evaluation = new ProductEvaluation();
        evaluation.setProduct(product);
        evaluation.setFinalScore(finalScore);
        evaluation.setReasonCodes(buildReasons(protection, affordability, risk, context));

        return evaluation;
    }

    // ---------------- Scoring Rules ----------------

    private int scoreProtection(ProductResponse p, CustomerIntelligenceProfile profile) {
        int gap = profile.getCoverageGap().get("LIFE");

        if (gap <= 0) return 40;

        if (p.getCoverageAmount() >= gap) return 100;
        if (p.getCoverageAmount() >= gap * 0.5) return 70;

        return 40;
    }

    private int scoreAffordability(ProductResponse p, CustomerIntelligenceProfile profile) {
        double ratio = (double) p.getMonthlyPremium() / profile.getMaxAffordablePremium();

        if (ratio <= 0.5) return 100;
        if (ratio <= 0.8) return 70;
        return 40;
    }

    private int scoreRisk(ProductResponse p, CustomerIntelligenceProfile profile) {
        if ("HIGH".equals(profile.getRiskTier()) && p.getMaxRiskScore() >= 7) return 80;
        if ("MEDIUM".equals(profile.getRiskTier()) && p.getMaxRiskScore() >= 5) return 80;
        return 60;
    }

    private int scoreContext(ProductResponse p, CustomerIntelligenceProfile profile) {
        int score = 40;

        if (profile.getFamilyProfile().isHasChildren()
                && "FAMILY".equalsIgnoreCase(p.getTargetFamilyType())) {
            score += 30;
        }

        if (profile.isFirstTimeBuyer()
                && Boolean.TRUE.equals(p.getRecommendedForFirstTimeBuyer())) {
            score += 20;
        }

        return score;
    }

    private List<String> buildReasons(int p, int a, int r, int c) {
        List<String> reasons = new ArrayList<>();
        if (p >= 70) reasons.add("COVERS_COVERAGE_GAP");
        if (a >= 70) reasons.add("AFFORDABLE_PREMIUM");
        if (r >= 70) reasons.add("RISK_COMPATIBLE");
        if (c >= 60) reasons.add("FAMILY_CONTEXT_MATCH");
        return reasons;
    }
}
