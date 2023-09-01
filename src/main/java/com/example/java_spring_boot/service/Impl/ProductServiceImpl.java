package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.dao.repository.MockProductDAO;
import com.example.java_spring_boot.dto.request.ProductListRequest;
import com.example.java_spring_boot.dto.response.Product;
import com.example.java_spring_boot.service.ProductService;
import com.example.java_spring_boot.util.exception.NotFoundException;
import com.example.java_spring_boot.util.exception.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired // Spring Boot 啟動時便會給該變數傳入物件，這個特性稱為「 依賴注入」（dependency injection）
    MockProductDAO productDAO;

    @Override
    public Product getProduct(String id) {
        return productDAO.find(id)
                .orElseThrow(() -> new NotFoundException("Can't find product."));
    }

    @Override
    public Product createProduct(Product request) {
        boolean isIdDuplicated = productDAO.find(request.getId()).isPresent();
        if (isIdDuplicated) {
            throw new UnprocessableEntityException("The id of the product is duplicated.");
        }

        Product product = new Product();
        product.setId(request.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return productDAO.insert(product);
    }

    @Override
    public Product updateProduct(String id, Product request) {
        Product product = getProduct(id);
        return productDAO.update(product.getId(), request);
    }

    @Override
    public void deleteProductById(String id) {
        Product product = getProduct(id);
        productDAO.delete(product.getId());
    }

    @Override
    public List<Product> getProducts(ProductListRequest request) {
        return productDAO.find(request);
    }
}
