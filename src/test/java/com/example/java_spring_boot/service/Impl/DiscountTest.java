package com.example.java_spring_boot.service.Impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class DiscountTest {

    @Autowired
    private DiscountCalculator discountCalculator;

    @Test
    public void testDiscountCalculator() {
        DiscountCalculator calculator = new DiscountCalculator();

        Assert.assertEquals(0, calculator.calcDiscount(30));
        Assert.assertEquals(10, calculator.calcDiscount(60, 50));
        Assert.assertEquals(8, calculator.calcDiscount(40, 55, 50));
        Assert.assertEquals(18, calculator.calcDiscount(70, 40, 50, 65));
    }
}
