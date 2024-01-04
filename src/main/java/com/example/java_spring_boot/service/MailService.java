package com.example.java_spring_boot.service;

import com.example.java_spring_boot.dto.request.SendMailRequest;
import org.springframework.stereotype.Service;

@Service
public interface MailService {

    void sendMail(SendMailRequest request);
    void sendNewProductMail(String productId);
    void sendDeleteProductMail(String productId);
}
