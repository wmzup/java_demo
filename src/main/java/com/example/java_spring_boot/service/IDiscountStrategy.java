package com.example.java_spring_boot.service;

public interface IDiscountStrategy {

    int calcDiscount(int...prices);
    int calcDiscount(int priceA, int priceB);
}
