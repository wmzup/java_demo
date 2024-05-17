package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.service.IDiscountStrategy;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class DiscountTest {

    @Autowired
    private DiscountCalculator calculator;

    @Test
    public void testPercantDiscountCalculator() {
        IDiscountStrategy strategy = new PercentDiscountStrategy();
        DiscountCalculator calculator = new DiscountCalculator(strategy);

        Assert.assertEquals(0, calculator.calcDiscount(30));
        Assert.assertEquals(10, calculator.calcDiscount(60, 50));
        Assert.assertEquals(8, calculator.calcDiscount(40, 55, 50));
        Assert.assertEquals(18, calculator.calcDiscount(70, 40, 50, 65)); // (40+50)*0.2
    }

    @Test
    public void testFixedPriceDiscountCalculator() {
        IDiscountStrategy strategy = new FixedPriceDiscountStrategy();
        DiscountCalculator calculator = new DiscountCalculator(strategy);

        Assert.assertEquals(0, calculator.calcDiscount(30));
        Assert.assertEquals(40, calculator.calcDiscount(60, 50));
        Assert.assertEquals(25, calculator.calcDiscount(55, 50, 35));
        Assert.assertEquals(70, calculator.calcDiscount(70, 40, 50, 65)); // (40-10) + (50-10)
    }
}
