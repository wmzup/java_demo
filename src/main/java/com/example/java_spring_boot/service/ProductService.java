package com.example.java_spring_boot.service;

import com.example.java_spring_boot.dao.repository.MockProductDAO;
import com.example.java_spring_boot.dto.response.Product;
import com.example.java_spring_boot.util.exception.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    MockProductDAO productDAO;

    public Product createProduct(Product request) {
        boolean isIdDuplicated = productDAO.find(request.getId()).isPresent();
        if (isIdDuplicated) {
            throw new UnprocessableEntityException("The id of the product is duplicated.");
        }

        Product product = new Product();
        product.setId(request.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return productDAO.insert(product);
    }
}
