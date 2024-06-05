package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.converter.ProductConverter;
import com.example.java_spring_boot.dao.repository.ProductRepository;
import com.example.java_spring_boot.dto.request.ProductRequest;
import com.example.java_spring_boot.dto.response.Product;
import com.example.java_spring_boot.dto.response.ProductResponse;
import com.example.java_spring_boot.util.UserIdentity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductTest {

    private HttpHeaders httpHeaders;

    @Before
    public void init() {
        // 測試前初始化HttpHeaders物件
        httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    public void testProductConverterToResponse() {
        Product product = new Product();
        product.setId("1");
        product.setName("Harry Potter");
        product.setPrice(450);
        ProductResponse response = ProductConverter.toProductResponse(product);
        Assertions.assertEquals(product.getId(), response.getId());
        Assertions.assertEquals(product.getName(), response.getName());
        Assertions.assertEquals(product.getPrice(), response.getPrice());
    }

    @Test
    public void testCreateProduct() {

        ProductRepository productRepository = mock(ProductRepository.class);
        UserIdentity userIdentity = mock(UserIdentity.class);

        Integer creatorId = 123;
        when(userIdentity.getId()).thenReturn(creatorId);
        ProductServiceImpl productService = new ProductServiceImpl(productRepository, userIdentity);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Snack");
        productRequest.setPrice(50);
        productRequest.setCreatorId(123);
        ProductResponse productResponse = productService.createProduct(productRequest);

        verify(userIdentity).getId();
        verify(productRepository).insert(any(Product.class));

        Assert.assertEquals(productRequest.getName(), productResponse.getName());
        Assert.assertEquals(productRequest.getPrice(), productResponse.getPrice());
        Assert.assertEquals(productRequest.getCreatorId(), productResponse.getCreatorId());
    }

    @Test
    public void testGetProductById() {
        Product product = new Product();
        product.setId("productId");
        product.setName("productName");
        product.setPrice(50);
        product.setCreatorId(123);

        ProductRepository productRepository = mock(ProductRepository.class);
        when(productRepository.findById(product.getId()))
                .thenReturn(Optional.of(product));

        ProductServiceImpl productService = new ProductServiceImpl(productRepository, null);

        ProductResponse resultProduct = productService.getProduct(product.getId());
        Assert.assertEquals(product.getId(), resultProduct.getId());
        Assert.assertEquals(product.getName(), resultProduct.getName());
        Assert.assertEquals(product.getPrice(), resultProduct.getPrice());
        Assert.assertEquals(product.getCreatorId(), resultProduct.getCreatorId());
    }

}
