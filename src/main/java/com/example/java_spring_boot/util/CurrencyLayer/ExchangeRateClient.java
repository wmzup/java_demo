package com.example.java_spring_boot.util.CurrencyLayer;

import com.example.java_spring_boot.dto.response.ExchangeRateClientResponse;

import java.util.Collection;

public interface ExchangeRateClient {
    ExchangeRateClientResponse getLiveExchangeRates(String sourceCurrency, Collection<String> targetCurrencies);
}
