package com.example.java_spring_boot.dto.response;

import java.util.Map;

public interface ExchangeRateClientResponse {
    long getTimestamp();
    String getSource();
    Map<String, Double> getExchangeTable();

    default Double getRate(String currency) {
        String key = getSource() + currency;
        return getExchangeTable().get(key);
    }
}
