package com.example.java_spring_boot.dto.response;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "products")
public class Product {
    private String id;
    private String name;
    private Integer price;
    private Integer creatorId;

    public Product(String id, String name, Integer price, Integer creatorId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.creatorId = creatorId;
    }

    public Product() {

    }
}
