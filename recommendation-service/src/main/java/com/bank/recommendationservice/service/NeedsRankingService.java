package com.bank.recommendationservice.service;

import com.bank.recommendationservice.model.engine.CustomerIntelligenceProfile;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NeedsRankingService {

    public List<String> rankNeeds(CustomerIntelligenceProfile profile) {

        Map<String, Integer> scores = new HashMap<>();

        scores.put("LIFE", 0);
        scores.put("HEALTH", 0);
        scores.put("ASSET", 0);
        scores.put("LIFESTYLE", 0);

        // LIFE rules
        if (profile.getFamilyProfile().isHasChildren())
            scores.put("LIFE", scores.get("LIFE") + 40);

        if (profile.getFamilyProfile().isHasSpouse())
            scores.put("LIFE", scores.get("LIFE") + 30);

        if (profile.getCoverageGap().get("LIFE") > 0)
            scores.put("LIFE", scores.get("LIFE") + 50);

        if ("HIGH".equals(profile.getRiskTier()))
            scores.put("LIFE", scores.get("LIFE") + 20);

        // HEALTH rules
        if (profile.getFamilyProfile().isHasDependentParents())
            scores.put("HEALTH", scores.get("HEALTH") + 40);

        if (profile.getCoverageGap().get("HEALTH") > 0)
            scores.put("HEALTH", scores.get("HEALTH") + 50);

        if ("HIGH".equals(profile.getRiskTier()))
            scores.put("HEALTH", scores.get("HEALTH") + 20);

        // ASSET rules
        if (profile.getCoverageGap().get("ASSET") > 0)
            scores.put("ASSET", scores.get("ASSET") + 40);

        // LIFESTYLE rules
        if ("Active".equalsIgnoreCase(profile.getLifestyle()))
            scores.put("LIFESTYLE", scores.get("LIFESTYLE") + 20);

        // Sort by score descending
        return scores.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }
}
