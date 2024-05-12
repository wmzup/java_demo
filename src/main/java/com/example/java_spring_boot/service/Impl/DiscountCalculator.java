package com.example.java_spring_boot.service.Impl;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DiscountCalculator {

    private PercentDiscountStrategy discountStrategy = new PercentDiscountStrategy(0.2);

    public int calcDiscount(int...prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Please pass prices");
        }

        boolean hasIllegalPrice = Arrays.stream(prices).anyMatch(price -> price < 0);
        if (hasIllegalPrice) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        return discountStrategy.calcDiscount(prices);
    }
}
