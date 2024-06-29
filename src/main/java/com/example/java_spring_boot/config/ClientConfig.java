package com.example.java_spring_boot.config;

import com.example.java_spring_boot.util.CurrencyLayer.CurrencyLayerClient;
import com.example.java_spring_boot.util.CurrencyLayer.ExchangeRateClient;
import com.example.java_spring_boot.util.ipapi.IpApiClient;
import com.example.java_spring_boot.util.ipapi.IpInfoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ClientConfig {

    @Bean(name = "ipApiClient")
    public IpInfoClient ipApiClient(@Value("${client.timeout.second}") int timeoutSeconds) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("https://ipapi.co")
                .setConnectTimeout(Duration.ofSeconds(timeoutSeconds))
                .build();
        return new IpApiClient(restTemplate);
    }

    @Bean(name = "currencyLayerClient")
    public ExchangeRateClient currencyLayerClient(
            @Value("${client.timeout.second}") int timeoutSeconds,
            @Value("${currency-layer-client.access-key}") String accessKey) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("http://apilayer.net/api")
                .setConnectTimeout(Duration.ofSeconds(timeoutSeconds))
                .build();
        return new CurrencyLayerClient(restTemplate, accessKey);
    }
}
