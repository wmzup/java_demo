package com.example.java_spring_boot.config;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class MailConfig {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.protocol}")
    private String protocol;

    @Value("${mail.enable_auth}")
    private boolean authEnabled;

    @Value("${mail.enabled_starttls}")
    private boolean starttlsEnabled;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;
}
