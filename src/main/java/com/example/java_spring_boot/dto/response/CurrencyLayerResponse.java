package com.example.java_spring_boot.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CurrencyLayerResponse implements ExchangeRateClientResponse {
    private long timestamp;
    private String source;
    private Map<String, Double> quotes;

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public Map<String, Double> getExchangeTable() {
        return quotes;
    }
}
