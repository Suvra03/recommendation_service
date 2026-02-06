package com.bank.recommendationservice.service;

import com.bank.recommendationservice.model.engine.ProductEvaluation;
import org.springframework.stereotype.Service;

import java.util.StringJoiner;

@Service
public class ExplanationService {

    public String buildExplanation(ProductEvaluation evaluation) {

        StringJoiner joiner = new StringJoiner(", ");

        for (String reason : evaluation.getReasonCodes()) {
            switch (reason) {
                case "AFFORDABLE_PREMIUM" ->
                        joiner.add("fits comfortably within your monthly budget");
                case "RISK_COMPATIBLE" ->
                        joiner.add("matches your risk profile");
                case "FAMILY_CONTEXT_MATCH" ->
                        joiner.add("provides strong protection for your family");
                case "COVERS_COVERAGE_GAP" ->
                        joiner.add("helps close your life insurance coverage gap");
            }
        }

        return "We recommended this plan because it " + joiner.toString() + ".";
    }
}
