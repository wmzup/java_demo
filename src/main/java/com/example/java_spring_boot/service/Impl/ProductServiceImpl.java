package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.converter.ProductConverter;
import com.example.java_spring_boot.dao.repository.MockProductDAO;
import com.example.java_spring_boot.dao.repository.ProductRepository;
import com.example.java_spring_boot.dto.request.ProductListRequest;
import com.example.java_spring_boot.dto.request.ProductRequest;
import com.example.java_spring_boot.dto.response.Product;
import com.example.java_spring_boot.dto.response.ProductResponse;
import com.example.java_spring_boot.service.ProductService;
import com.example.java_spring_boot.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired // Spring Boot 啟動時便會給該變數傳入物件，這個特性稱為「 依賴注入」（dependency injection）
    MockProductDAO productDAO;

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductResponse getProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find product."));
        return ProductConverter.toProductResponse(product);

    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = ProductConverter.toProduct(request);
        return productRepository.insert(product);
    }

    @Override
    public Product updateProduct(String id, ProductRequest request) {
        Product oldProduct = ProductConverter.toProduct(request);
        Product newProduct = ProductConverter.toProduct(request);
        newProduct.setId(oldProduct.getId());
        return productRepository.save(newProduct);
    }

    @Override
    public void deleteProductById(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProducts(ProductListRequest request) {

        String keyword = Optional.ofNullable(request.getKeyword()).orElse("");
        int priceFrom = Optional.ofNullable(request.getPriceFrom()).orElse(0);
        int priceTo = Optional.ofNullable(request.getPriceTo()).orElse(Integer.MAX_VALUE);
        Sort sort = genSortingStrategy(request.getOrderBy(), request.getSortRule());
        return productRepository.findByPriceBetweenAndNameLikeIgnoreCase(priceFrom, priceTo, keyword, sort);
    }

    private Sort genSortingStrategy(String orderBy, String sortRule) {

        Sort sort = Sort.unsorted();
        if (Objects.nonNull(orderBy) && Objects.nonNull(sortRule)) {
            Sort.Direction direction = Sort.Direction.fromString(sortRule);
            sort = Sort.by(direction, orderBy);
        }

        return sort;
    }
}
