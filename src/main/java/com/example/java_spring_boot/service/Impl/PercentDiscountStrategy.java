package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.service.IDiscountStrategy;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component("percentDiscountStrategy")
public class PercentDiscountStrategy implements IDiscountStrategy {

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

    @Override
    public int calcDiscount(int priceA, int priceB) {
        int minPrice = Math.min(priceA, priceB);
        return (int) Math.round(minPrice * discountRate);
    }
}
