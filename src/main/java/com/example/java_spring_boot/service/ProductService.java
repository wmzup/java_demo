package com.example.java_spring_boot.service;

import com.example.java_spring_boot.dto.request.ProductListRequest;
import com.example.java_spring_boot.dto.request.ProductRequest;
import com.example.java_spring_boot.dto.response.Product;
import com.example.java_spring_boot.dto.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    ProductResponse getProduct(String id);

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(String id, ProductRequest request);

    void deleteProductById(String id);

    List<ProductResponse> getProducts(ProductListRequest request);
}
