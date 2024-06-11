package com.example.java_spring_boot.dao.repository;

import com.example.java_spring_boot.dto.response.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByPriceBetweenAndNameLikeIgnoreCase(Integer priceFrom, Integer priceTo, String name, Sort sort);
}
