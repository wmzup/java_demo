package com.example.java_spring_boot.util.CurrencyLayer;

import com.example.java_spring_boot.dto.response.CurrencyLayerResponse;
import com.example.java_spring_boot.dto.response.ExchangeRateClientResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;

@Component
public class CurrencyLayerClient implements ExchangeRateClient {

    private RestTemplate restTemplate;
    private final String accessKey;

    public CurrencyLayerClient(RestTemplate restTemplate, String accessKey) {
        this.restTemplate = restTemplate;
        this.accessKey = accessKey;
    }

    @Override
    public ExchangeRateClientResponse getLiveExchangeRates(String sourceCurrency, Collection<String> targetCurrencies) {
        Map<String, String> uriVariables = Map.of(
                "source", sourceCurrency,
                "currencies", String.join(",", targetCurrencies),
                "access_key", accessKey
        );

        return restTemplate.getForObject(
                "/live?format=1&source={source}&currencies={currencies}&access_key={access_key}",
                CurrencyLayerResponse.class,
                uriVariables
        );
    }
}
