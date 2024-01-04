package com.example.java_spring_boot.config;

import com.example.java_spring_boot.dao.repository.MockProductDAO;
import com.example.java_spring_boot.dao.repository.ProductRepository;
import com.example.java_spring_boot.service.Impl.ProductServiceImpl;
import com.example.java_spring_boot.service.MailService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ServiceConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ProductServiceImpl productService(ProductRepository productRepository, MockProductDAO mockProductDAO,
                                             MailService mailService) {
        System.out.println("product service is created");
        return new ProductServiceImpl(productRepository, mockProductDAO, mailService);
    }
}
