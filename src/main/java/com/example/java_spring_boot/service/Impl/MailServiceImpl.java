package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.dto.request.SendMailRequest;
import com.example.java_spring_boot.service.MailService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;
    private static final boolean ENABLE_AUTH = true;
    private static final boolean ENABLE_STARTTLS = true;
    private static final String PROTOCOL = "smtp";
    private static final String USERNAME = "wmzup124@gmail.com";
    private static final String PASSWORD = "";
    private JavaMailSenderImpl mailSender;

    @PostConstruct
    public void init() {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(HOST);
        mailSender.setPort(PORT);
        mailSender.setUsername(USERNAME);
        mailSender.setPassword(PASSWORD);

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", ENABLE_AUTH);
        properties.put("mail.smtp.starttls.enable", ENABLE_STARTTLS);
        properties.put("mail.transport.protocol", PROTOCOL);
    }

    @Override
    public void sendMail(SendMailRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(USERNAME);
        message.setTo(request.getReceivers());
        message.setSubject(request.getSubject());
        message.setText(request.getContent());

        try {
            mailSender.send(message);
        } catch (MailAuthenticationException e) {
            LOGGER.error(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
    }
}
