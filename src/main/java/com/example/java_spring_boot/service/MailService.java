package com.example.java_spring_boot.service;

import com.example.java_spring_boot.dto.request.SendMailRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MailService {

    void sendMail(SendMailRequest request);
    void sendNewProductMail(String productId);
    void sendDeleteProductMail(String productId);

    void sendMail(String subject, String content, List<String> receivers);
}
