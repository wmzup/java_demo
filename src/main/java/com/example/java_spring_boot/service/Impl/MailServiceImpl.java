package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.dto.request.SendMailRequest;
import com.example.java_spring_boot.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailServiceImpl implements MailService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final JavaMailSenderImpl mailSender;

    // 這裡原本注入時會出現：Could not autowire. No beans of 'JavaMailSenderImpl' type found
    // 原以為是因為配置問題，但功能其實是可以使用的
    // 既然不影響運行，就降低Autowired檢測的級別。將Severity的級別由error改成warning或其他可以忽略的級別
    // 參考：https://www.cnblogs.com/spring-ioc/p/16100568.html
    public MailServiceImpl(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(SendMailRequest request) {
        // 實測結果只有outlook會寄信，gmail跟yahoo都沒做事，但也沒噴error。需另外找時間爬文
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailSender.getUsername());
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
