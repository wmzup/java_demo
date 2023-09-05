package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.dao.repository.MockProductDAO;
import com.example.java_spring_boot.dao.repository.ProductRepository;
import com.example.java_spring_boot.dto.request.ProductListRequest;
import com.example.java_spring_boot.dto.response.Product;
import com.example.java_spring_boot.service.ProductService;
import com.example.java_spring_boot.util.exception.NotFoundException;
import com.example.java_spring_boot.util.exception.UnprocessableEntityException;
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
    public Product getProduct(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find product."));
    }

    @Override
    public Product createProduct(Product request) {

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return productRepository.insert(product);
    }

    @Override
    public Product updateProduct(String id, Product request) {

        Product oldProduct = getProduct(id);

        Product product = new Product();
        product.setId(oldProduct.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return productRepository.save(product);
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
