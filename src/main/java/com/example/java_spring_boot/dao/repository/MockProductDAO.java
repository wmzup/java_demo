package com.example.java_spring_boot.dao.repository;

import com.example.java_spring_boot.dto.request.ProductListRequest;
import com.example.java_spring_boot.dto.response.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MockProductDAO {
    private final List<Product> productDB = new ArrayList<>();
    @PostConstruct  // class被建立後，自動執行該方法，新增預設的產品資料
    private void initDB() {
        productDB.add(new Product("B0001", "Android Development (Java)", 380));
        productDB.add(new Product("B0002", "Android Development (Kotlin)", 420));
        productDB.add(new Product("B0003", "Data Structure (Java)", 250));
        productDB.add(new Product("B0004", "Finance Management", 450));
        productDB.add(new Product("B0005", "Human Resource Management", 330));
    }

    public Product insert(Product product) {
        productDB.add(product);
        return product;
    }

    public Product update(String id, Product product) {
        Optional<Product> productOpt = find(id);
        productOpt.ifPresent(p -> {
            p.setName(product.getName());
            p.setPrice(product.getPrice());
        });

        return product;
    }

    public void delete(String id) {
        // 透過 id 指定要刪除的資源，直接從 List 移除
        productDB.removeIf(p -> p.getId().equals(id));
    }

    public Optional<Product> find(String id) {
        return productDB.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public List<Product> find(ProductListRequest request) {
        String keyword = Optional.ofNullable(request.getKeyword()).orElse("");
        String orderBy = request.getOrderBy();
        String sortRule = request.getSortRule();
        Comparator<Product> comparator = genSortComparator(orderBy, sortRule);

        return productDB.stream()
                .filter(p -> p.getName().contains(keyword))
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private Comparator<Product> genSortComparator(String orderBy, String sortRule) {
        Comparator<Product> comparator = (p1, p2) -> 0;

        if (Objects.isNull(orderBy) || Objects.isNull(sortRule)) {
            return comparator;
        }

        if (orderBy.equalsIgnoreCase("price")) {
            comparator = Comparator.comparing(Product::getPrice);
        } else if (orderBy.equalsIgnoreCase("name")) {
            comparator = Comparator.comparing(Product::getName);
        }

        return sortRule.equalsIgnoreCase("desc")
                ? comparator.reversed()
                : comparator;
    }
}