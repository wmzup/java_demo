package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.dao.repository.ProductRepository;
import com.example.java_spring_boot.dto.response.Product;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductTest {

    private HttpHeaders httpHeaders;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void init() {
        // 測試前初始化HttpHeaders物件
        httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    public void testCreateProduct() throws Exception {
        JSONObject request = new JSONObject()
                .put("name", "Harry Potter")
                .put("price", 450);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .headers(httpHeaders)
                .content(request.toString());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").hasJsonPath())
                .andExpect(jsonPath("$.name").value(request.getString("name")))
                .andExpect(jsonPath("$.price").value(request.getInt("price")))
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testGetProductById() throws Exception {
        Product product = createProduct("Economics", 450);
        productRepository.insert(product);

        mockMvc.perform(get("/products/" + product.getId())
                .headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Product product = createProduct("Economics", 450);
        productRepository.insert(product);

        JSONObject request = new JSONObject()
            .put("name", "Macroeconomics")
            .put("price", 550);

        mockMvc.perform(put("products" + product.getId())
                .headers(httpHeaders)
                .content(request.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Product product = createProduct("Economics", 450);
        productRepository.insert(product);

        mockMvc.perform(delete("/products/" + product.getId())
                .headers(httpHeaders))
                .andExpect(status().isNoContent());

        productRepository.findById(product.getId())
                .orElseThrow(RuntimeException::new);
    }

    @Test
    public void testSearchProductsSortByPriceAsc() throws Exception {
        Product p1 = createProduct("Operation Management", 350);
        Product p2 = createProduct("Marketing Management", 200);
        Product p3 = createProduct("Human Resource Management", 420);
        Product p4 = createProduct("Finance Management", 400);
        Product p5 = createProduct("Enterprise Resource Planning", 440);
        productRepository.insert(Arrays.asList(p1, p2, p3, p4, p5));

        mockMvc.perform(get("/products")
                .headers(httpHeaders)
                .param("keyword", "Manage")
                .param("orderBy", "price")
                .param("sortRule", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id").value(p2.getId()))
                .andExpect(jsonPath("$[1].id").value(p1.getId()))
                .andExpect(jsonPath("$[2].id").value(p4.getId()))
                .andExpect(jsonPath("$[3].id").value(p3.getId()));
    }

    @Test
    public void testSearchProductsSortByPriceAsc_Assertion() throws Exception {
        Product p1 = createProduct("Operation Management", 350);
        Product p2 = createProduct("Marketing Management", 200);
        Product p3 = createProduct("Human Resource Management", 420);
        Product p4 = createProduct("Finance Management", 400);
        Product p5 = createProduct("Enterprise Resource Planning", 440);
        productRepository.insert(Arrays.asList(p1, p2, p3, p4, p5));

        MvcResult result = mockMvc.perform(get("products")
                        .headers(httpHeaders)
                        .param("keyword", "Manage")
                        .param("orderBy", "price")
                        .param("sortRule", "asc"))
                        .andReturn();

        MockHttpServletResponse mockHttpResponse = result.getResponse();
        String responseJSONStr = mockHttpResponse.getContentAsString();
        JSONArray productJSONArray = new JSONArray(responseJSONStr);

        List<String> productIds = new ArrayList<>();
        for (int i = 0; i < productJSONArray.length(); i++) {
            JSONObject productJSON = productJSONArray.getJSONObject(i);
            productIds.add(productJSON.getString("id"));
        }

        Assertions.assertEquals(4, productIds.size());
        Assertions.assertEquals(p2.getId(), productIds.get(0));
        Assertions.assertEquals(p1.getId(), productIds.get(1));
        Assertions.assertEquals(p4.getId(), productIds.get(2));
        Assertions.assertEquals(p3.getId(), productIds.get(3));

        Assertions.assertEquals(HttpStatus.OK.value(), mockHttpResponse.getStatus());
        Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE,
                mockHttpResponse.getHeader(HttpHeaders.CONTENT_TYPE));
    }

    @Test
    public void get400WhenCreateProductWithEmptyName() throws Exception {
        JSONObject request = new JSONObject()
                .put("name", "")
                .put("price", 350);

        mockMvc.perform(post("/products")
                        .headers(httpHeaders)
                        .content(request.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void get400WhenUpdateProductWithNegativePrice() throws Exception {
        Product product = createProduct("Computer Science", 350);
        productRepository.insert(product);

        JSONObject request = new JSONObject()
                .put("name", "Computer Science")
                .put("price", -100);

        mockMvc.perform(put("/products" + product.getId())
                .headers(httpHeaders)
                .content(request.toString()))
                .andExpect(status().isBadRequest());
    }

    @After
    public void clear() {
        // 測試後清除資料庫
        productRepository.deleteAll();
    }

    private Product createProduct(String name, int price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        return product;
    }

}
