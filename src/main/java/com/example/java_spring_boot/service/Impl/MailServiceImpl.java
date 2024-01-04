package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.dto.request.SendMailRequest;
import com.example.java_spring_boot.service.MailService;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MailServiceImpl implements MailService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final JavaMailSenderImpl mailSender;
    private final long tag;
    private final List<String> mailMessages;
    private final String LOG_EMAIL;


    // 這裡原本注入時會出現：Could not autowire. No beans of 'JavaMailSenderImpl' type found
    // 原以為是因為配置問題，但功能其實是可以使用的
    // 既然不影響運行，就降低Autowired檢測的級別。將Severity的級別由error改成warning或其他可以忽略的級別
    // 參考：https://www.cnblogs.com/spring-ioc/p/16100568.html
    public MailServiceImpl(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
        this.tag = System.currentTimeMillis();
        this.mailMessages = new ArrayList<>();
        this.LOG_EMAIL = mailSender.getUsername();
    }

    @Override
    public void sendMail(SendMailRequest request) {
        sendMail(request.getSubject(), request.getContent(), request.getReceivers());
    }

    @Override
    public void sendNewProductMail(String productId) {
        // Arrays.asList(strArray)返回值是仍然是一個可變的集合，但返回值是其內部類，不具有add()，可以通過set()增加值，默認長度是10
        // Collections.singletonList: 返回不可變的集合，但這長度的集合只有1，可以減少內存。但返回的值依然是Collection的內部實現類
        // 同樣沒有add()，調用add()或set()會報錯
        String content = String.format("There's a new create product (%s).", productId);
        sendMail("New Product", content, Collections.singletonList(LOG_EMAIL));
    }

    @Override
    public void sendDeleteProductMail(String productId) {
        String content = String.format("There's a product deleted (%s).", productId);
        sendMail("Product Deleted", content, Collections.singletonList(LOG_EMAIL));
    }

    public void sendMail(String subject, String content, List<String> receivers) {
        // 實測結果只有outlook會寄信，gmail跟yahoo都沒做事，但也沒噴error。需另外找時間爬文
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailSender.getUsername());
        message.setTo(receivers.toArray(new String[0]));
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            mailMessages.add(content);
            printMessages();
        } catch (MailAuthenticationException e) {
            LOGGER.error(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
    }

    private void printMessages() {
        System.out.println("----------");
        mailMessages.forEach(System.out::println);
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("##########");
        System.out.printf("Spring boot is about to destroy Mail Service %d", tag);
    }
}
