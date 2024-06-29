package com.example.java_spring_boot.util.ipapi;

import com.example.java_spring_boot.dto.response.IpApiResponse;
import com.example.java_spring_boot.dto.response.IpInfoClientResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Map;

@Component
public class IpApiClient implements IpInfoClient {

    private RestTemplate restTemplate;

    public IpApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public IpInfoClientResponse getIpInfo(String ipAddress) {
        return restTemplate.getForObject(
                "/{ip}/json",
                IpApiResponse.class,
                Map.of("ip", ipAddress)
        );
    }
}
