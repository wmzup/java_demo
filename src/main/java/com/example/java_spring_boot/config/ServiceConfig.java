package com.example.java_spring_boot.config;

import com.example.java_spring_boot.dao.repository.MockProductDAO;
import com.example.java_spring_boot.dao.repository.ProductRepository;
import com.example.java_spring_boot.service.Impl.ProductServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ProductServiceImpl productService(ProductRepository productRepository, MockProductDAO mockProductDAO) {
        return new ProductServiceImpl(productRepository, mockProductDAO);
    }
}
