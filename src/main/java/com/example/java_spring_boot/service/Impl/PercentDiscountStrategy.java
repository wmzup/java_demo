package com.example.java_spring_boot.service.Impl;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PercentDiscountStrategy {

    private double discountRate;

    public PercentDiscountStrategy(double discountRate) {
        this.discountRate = discountRate;
    }

    public int calcDiscount(int... prices) {
        int sumOfLowerPrices = Arrays.stream(prices)
                .sorted()
                .limit(prices.length / 2)
                .sum();
        return (int) Math.round(sumOfLowerPrices * discountRate);
    }
}
