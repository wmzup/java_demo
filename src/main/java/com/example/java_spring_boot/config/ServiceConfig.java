package com.example.java_spring_boot.config;

import com.example.java_spring_boot.dao.repository.MockProductDAO;
import com.example.java_spring_boot.dao.repository.ProductRepository;
import com.example.java_spring_boot.service.Impl.ProductServiceImpl;
import com.example.java_spring_boot.service.MailService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class ServiceConfig {

    @Bean
    // 這會將 ProductService 設置成每次呼叫時就建立一個全新的元件。且 Spring 啟動時不會馬上建立，而是等到它的方法被呼叫時才建立
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ProductServiceImpl productService(ProductRepository productRepository, MockProductDAO mockProductDAO,
                                             MailService mailService) {
        System.out.println("product service is created");
        return new ProductServiceImpl(productRepository, mockProductDAO, mailService);
    }
}
