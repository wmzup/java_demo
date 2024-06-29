package com.example.java_spring_boot.util;

import com.example.java_spring_boot.dto.response.CurrencyLayerResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;

@Component
public class CurrencyLayerClient {

    private static final String ACCESS_KEY = "065d476ec043ce31ce89ab9d6fbb45bc";
    private static final int TIMEOUT_SECONDS = 20;

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplateBuilder()
                .rootUri("http://apilayer.net/api")
                .setConnectTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .build();
    }

    public CurrencyLayerResponse getLiveExchangeRates(String sourceCurrency, Collection<String> targetCurrencies) {
        Map<String, String> uriVariables = Map.of(
                "source", sourceCurrency,
                "currencies", String.join(",", targetCurrencies),
                "access_key", ACCESS_KEY
        );

        return restTemplate.getForObject(
                "/live?format=1&source={source}&currencies={currencies}&access_key={access_key}",
                CurrencyLayerResponse.class,
                uriVariables
        );
    }
}
