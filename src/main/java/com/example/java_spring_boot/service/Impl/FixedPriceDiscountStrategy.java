package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.service.IDiscountStrategy;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component("fixedPriceDiscountStrategy")
public class FixedPriceDiscountStrategy implements IDiscountStrategy {

    private int fixedPrice = 10;

    @Override
    public int calcDiscount(int priceA, int priceB) {
        return Math.min(priceA, priceB) - fixedPrice;
    }

    @Override
    public int calcDiscount(int...prices) {
        return Arrays.stream(prices)
                .sorted()
                .limit(prices.length / 2)
                .map(price -> price - fixedPrice)
                .sum();
    }
}
