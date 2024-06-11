package com.example.java_spring_boot.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {

    @NotEmpty(message = "Product name is undefined.")
    private String name;

    @NotNull
    @Min(value = 0, message = "Price should be greater or equal to 0.")
    private Integer price;

    @NotNull(message = "Creator id is undefined.")
    private Integer creatorId;
}
