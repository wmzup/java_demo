package com.example.java_spring_boot.util;

import com.example.java_spring_boot.dto.response.IpApiResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Map;

@Component
public class IpApiClient {
    // 此值可以選擇放在 application.properties，再注入進來
    private static final int TIMEOUT_SECONDS = 20;

    private RestTemplate restTemplate;

    @PostConstruct
    private void init() {
        restTemplate = new RestTemplateBuilder()
                .rootUri("https://ipapi.co")
                .setConnectTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .build();
    }

    public IpApiResponse getIpInfo(String ipAddress) {
        return restTemplate.getForObject(
                "/{ip}/json",
                IpApiResponse.class,
                Map.of("ip", ipAddress)
        );
    }
}
