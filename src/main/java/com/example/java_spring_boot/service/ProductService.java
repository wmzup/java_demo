package com.example.java_spring_boot.service;

import com.example.java_spring_boot.dto.request.ProductListRequest;
import com.example.java_spring_boot.dto.request.ProductRequest;
import com.example.java_spring_boot.dto.response.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    Product getProduct(String id);

    Product createProduct(ProductRequest request);

    Product updateProduct(String id, ProductRequest request);

    void deleteProductById(String id);

    List<Product> getProducts(ProductListRequest request);
}
