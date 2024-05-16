package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.service.IDiscountStrategy;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DiscountCalculator {

    private IDiscountStrategy discountStrategy;

    public DiscountCalculator(IDiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public int calcDiscount(int...prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Please pass prices");
        }

        boolean hasIllegalPrice = Arrays.stream(prices).anyMatch(price -> price < 0);
        if (hasIllegalPrice) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        return prices.length == 2
                ? discountStrategy.calcDiscount(prices[0], prices[1])
                : discountStrategy.calcDiscount(prices);
    }
}
