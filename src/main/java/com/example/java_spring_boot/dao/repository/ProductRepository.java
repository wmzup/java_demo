package com.example.java_spring_boot.dao.repository;

import com.example.java_spring_boot.dto.response.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByPriceBetweenAndNameLikeIgnoreCase(Integer priceFrom, Integer priceTo, String name, Sort sort);
}
