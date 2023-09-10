package com.example.java_spring_boot.dto.response;

import lombok.Data;

@Data
public class ProductResponse {
    private String id;
    private String name;
    private int price;
}
