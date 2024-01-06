package com.example.java_spring_boot.config;

import com.example.java_spring_boot.service.Impl.MailServiceImpl;
import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.context.WebApplicationContext;

import java.util.Properties;

@Data
@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {

    @Value("${mail.platform}")
    private String platform;
    @Value("${mail.protocol}")
    private String protocol;
    @Value("${mail.enable_auth}")
    private boolean authEnabled;
    @Value("${mail.enabled_starttls}")
    private boolean starttlsEnabled;
    @Value("${mail.outlook.host}")
    private String outlookHost;
    @Value("${mail.outlook.port}")
    private int outlookPort;
    @Value("${mail.outlook.username}")
    private String outlookUsername;
    @Value("${mail.outlook.password}")
    private String outlookPassword;
    @Value("${mail.gmail.host}")
    private String gmailHost;
    @Value("${mail.gmail.port}")
    private int gmailPort;
    @Value("${mail.gmail.username}")
    private String gmailUsername;
    @Value("${mail.gmail.password}")
    private String gmailPassword;
    @Value("${mail.yahoo.host}")
    private String yahooHost;
    @Value("${mail.yahoo.port}")
    private int yahooPort;
    @Value("${mail.yahoo.username}")
    private String yahooUsername;
    @Value("${mail.yahoo.password}")
    private String yahooPassword;

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public MailServiceImpl mailService() {
        JavaMailSenderImpl mailSender = null;

        if ("outlook".equals(platform)) {
            mailSender = outlookSender();
        }

        if ("gmail".equals(platform)) {
            mailSender = gmailSender();
        }

        if ("yahoo".equals(platform)) {
            mailSender = yahooSender();
        }

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", authEnabled);
        properties.put("mail.smtp.starttls.enable", starttlsEnabled);
        properties.put("mail.transport.protocol", protocol);

        System.out.println("Mail service is created");
        return new MailServiceImpl(mailSender);
    }

    private JavaMailSenderImpl outlookSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(outlookHost);
        mailSender.setPort(outlookPort);
        mailSender.setUsername(outlookUsername);
        mailSender.setPassword(outlookPassword);

        return mailSender;
    }

    private JavaMailSenderImpl gmailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(gmailHost);
        mailSender.setPort(gmailPort);
        mailSender.setUsername(gmailUsername);
        mailSender.setPassword(gmailPassword);

        return mailSender;
    }

    private JavaMailSenderImpl yahooSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(yahooHost);
        mailSender.setPort(yahooPort);
        mailSender.setUsername(yahooUsername);
        mailSender.setPassword(yahooPassword);

        return mailSender;
    }
}
