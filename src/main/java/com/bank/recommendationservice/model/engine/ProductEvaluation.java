package com.bank.recommendationservice.model.engine;

import com.bank.recommendationservice.model.product.ProductResponse;

import java.util.List;

public class ProductEvaluation {

    private ProductResponse product;
    private int finalScore;
    private List<String> reasonCodes;

    public ProductResponse getProduct() {
        return product;
    }

    public void setProduct(ProductResponse product) {
        this.product = product;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public List<String> getReasonCodes() {
        return reasonCodes;
    }

    public void setReasonCodes(List<String> reasonCodes) {
        this.reasonCodes = reasonCodes;
    }
}
