package com.example.java_spring_boot.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductListRequest {
    private String keyword;
    private Integer priceFrom;
    private Integer priceTo;
    private String orderBy;
    private String sortRule;
}
