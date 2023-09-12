package com.example.java_spring_boot.converter;

import com.example.java_spring_boot.dto.request.ProductRequest;
import com.example.java_spring_boot.dto.response.Product;
import com.example.java_spring_boot.dto.response.ProductResponse;

public class ProductConverter {

    private ProductConverter() {

    }

    public static Product toProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        return product;
    }

    public static ProductResponse toProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        return response;
    }
}
