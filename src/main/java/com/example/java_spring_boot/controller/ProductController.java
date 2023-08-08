package com.example.java_spring_boot.controller;

import com.example.java_spring_boot.dto.response.Product;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(
            @PathVariable("id")
            String id
    ) {
        Product product = new Product();
        product.setId(id);
        product.setName("myBook");
        product.setPrice(100);
        return ResponseEntity.ok().body(product);
    }
}
