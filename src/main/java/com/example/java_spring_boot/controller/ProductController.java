package com.example.java_spring_boot.controller;

import com.example.java_spring_boot.dto.request.ProductListRequest;
import com.example.java_spring_boot.dto.request.ProductRequest;
import com.example.java_spring_boot.dto.response.Product;
import com.example.java_spring_boot.dto.response.ProductResponse;
import com.example.java_spring_boot.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")    // @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") String id) {
        ProductResponse product = productService.getProduct(id); // 呼叫第一次
        product = productService.getProduct(id); // 呼叫第二次
        product = productService.getProduct(id); // 呼叫第三次
        return ResponseEntity.ok(product);
    }

    @PostMapping    // @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody @Valid ProductRequest request
    ) {
        ProductResponse product = productService.createProduct(request);
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
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable("id")
            String id,
            @RequestBody
            @Valid
            ProductRequest request) {
        ProductResponse product = productService.updateProduct(id, request);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}") // @RequestMapping(method = RequestMethod.HEAD)
    public ResponseEntity<Void> deleteProductById(
            @PathVariable
            String id
    ) {
        productService.deleteProductById(id);
        // 若資源原先是存在的，就回傳狀態碼204（No Content），意思跟200一樣是請求成功，但回應主體沒有內容
        // 若資源原先並不存在，則回傳狀態碼404
        // if (isRemoved) {
        //  return ResponseEntity.noContent().build();
        // }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(
            @ModelAttribute // 讓 Spring Boot 將查詢字串的值賦予給 ProductListRequest 物件
            ProductListRequest productListRequest
    ) {
        List<ProductResponse> products = productService.getProducts(productListRequest);
        return ResponseEntity.ok(products);
    }
}
