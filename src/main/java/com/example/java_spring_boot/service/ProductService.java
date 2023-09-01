package com.example.java_spring_boot.service;

import com.example.java_spring_boot.dto.request.ProductListRequest;
import com.example.java_spring_boot.dto.response.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    Product getProduct(String id);

    Product createProduct(Product request);

    Product updateProduct(String id, Product request);

    void deleteProductById(String id);

    List<Product> getProducts(ProductListRequest request);
}
