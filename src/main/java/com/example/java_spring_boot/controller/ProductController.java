package com.example.java_spring_boot.controller;

import com.example.java_spring_boot.dto.request.ProductListRequest;
import com.example.java_spring_boot.dto.response.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final List<Product> productDB = new ArrayList<>();

    // @PostConstruct Controller 被建立後，自動執行該方法，新增預設的產品資料
    @PostConstruct
    private void initDB() {
        productDB.add(new Product("B0001", "Android Development (Java)", 380));
        productDB.add(new Product("B0002", "Android Development (Kotlin)", 420));
        productDB.add(new Product("B0003", "Data Structure (Java)", 250));
        productDB.add(new Product("B0004", "Finance Management", 450));
        productDB.add(new Product("B0005", "Human Resource Management", 330));
    }

    @GetMapping("/{id}")    // @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> getProduct(
            @PathVariable("id")
            String id
    ) {
        Optional<Product> productOpt = productDB.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            return ResponseEntity.ok().body(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping    // @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> createProduct(
            @RequestBody
            Product request
    ) {
        boolean isIdDuplicated = productDB.stream()
                .anyMatch(product -> product.getId().equals(request.getId()));

        if (isIdDuplicated) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        Product product = new Product();
        product.setId(request.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        productDB.add(product);

        /*
         * 切換到「Headers」頁籤，這邊紀錄著「回應標頭」（response header）
         * 其中「Location」欄位值就是產品的 URI，它會指向這次新增的資源
         * 也就是說，對這個資源路徑發出 GET 請求，便能獲得該資源
         */
        URI location = ServletUriComponentsBuilder  // 建立 URI
                .fromCurrentRequest()   //以目前呼叫的資源路徑為基礎來建立 URI，此處為「http://…/products」
                .path("/{id}")  // 以目前的資源路徑再做延伸，定義新的路徑格式，可加入佔位符，此處為「http://…/products/{id}」
                .buildAndExpand(product.getId())    // 將參數填入路徑中的佔位符，產生真實的資源路徑，此處為「http://…/products/實際產品編號」
                .toUri();
        return ResponseEntity.created(location).body(product);
    }

    @PatchMapping("/{id}")  // @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Product> replacePlace(
            @PathVariable("id")
            String id,
            @RequestBody
            Product request) {
        Optional<Product> productOpt = productDB.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (!productOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Product product = productOpt.get();
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/{id}") // @RequestMapping(method = RequestMethod.HEAD)
    public ResponseEntity<Void> deleteProductById(
            @PathVariable
            String id
    ) {
        // 透過 id 指定要刪除的資源，直接從 List 移除
        boolean isRemoved = productDB.removeIf(p -> p.getId().equals(id));

        // 若資源原先是存在的，就回傳狀態碼204（No Content），意思跟200一樣是請求成功，但回應主體沒有內容
        // 若資源原先並不存在，則回傳狀態碼404。
        if (isRemoved) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(
            // 讓 Spring Boot 將查詢字串的值賦予給 ProductListRequest 物件
            @ModelAttribute
            ProductListRequest productListRequest
    ) {
        String keyword  = productListRequest.getKeyword();
        String orderBy  = productListRequest.getOrderBy();
        String sortRule = productListRequest.getSortRule();
        Comparator<Product> comparator = genComparator(orderBy, sortRule);

        List<Product> products = productDB.stream()
                .filter(p -> p.getName().toUpperCase().contains(keyword.toUpperCase()))
                .sorted(comparator)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(products);
    }

    private Comparator<Product> genComparator(String orderBy, String sortRule) {
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
