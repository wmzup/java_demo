package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.dto.request.CreateUserRequest;
import com.example.java_spring_boot.dto.request.CreateUserResponse;
import com.example.java_spring_boot.dto.response.GetUserListResponse;
import com.example.java_spring_boot.dto.response.SingleUserData;
import com.example.java_spring_boot.dto.response.SingleUserResponse;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.bson.assertions.Assertions.assertNotNull;

@SpringBootTest
public class RestTemplateTest {

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testGetSingleUser()
    {
        // 發送請求並取得回應
        org.springframework.http.ResponseEntity<com.example.java_spring_boot.dto.response.SingleUserResponse> response = restTemplate.getForEntity(
                "https://reqres.in/api/users/{id}",
                SingleUserResponse.class,
                Map.of("id", 1)
        );

        // 確認 HTTP 狀態碼
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // 確認 response header
        assertNotNull(response.getHeaders().getContentType());
        Assert.assertEquals("application/json;charset=utf-8", response.getHeaders().getContentType().toString());

        // 確認 response body
        SingleUserResponse responseBody = response.getBody();
        assertNotNull(responseBody);

        SingleUserData data = responseBody.getData();
        Assert.assertEquals(1, data.getId());
        Assert.assertEquals("george.bluth@reqres.in", data.getEmail());
        Assert.assertEquals("George", data.getFirstName());
        Assert.assertEquals("Bluth", data.getLastName());
        Assert.assertEquals("https://reqres.in/img/faces/1-image.jpg", data.getAvatar());
    }

    @Test
    public void testCreateUser() {
        CreateUserRequest request = CreateUserRequest.of("morpheus", "leader");
        CreateUserResponse response = restTemplate.postForObject(
                "https://reqres.in/api/users",
                request,
                CreateUserResponse.class
        );

        assertNotNull(response);
        Assert.assertEquals(request.getName(), response.getName());
        Assert.assertEquals(request.getJob(), response.getJob());
        assertNotNull(response.getId());
        assertNotNull(response.getCreatedAt());
    }

    @Test
    public void testGetUserList() {
        GetUserListResponse response = restTemplate.getForObject(
                "https://reqres.in/api/users?page={page}&per_page={per_page}",
                GetUserListResponse.class,
                Map.of("page", 2,
                        "per_page", 6)
        );

        assertNotNull(response);
        List<SingleUserData> users = response.getData();

        Assert.assertEquals(6, users.size());
        Assert.assertEquals(7, users.get(0).getId());
        Assert.assertEquals(8, users.get(1).getId());
        Assert.assertEquals(9, users.get(2).getId());
        Assert.assertEquals(10, users.get(3).getId());
        Assert.assertEquals(11, users.get(4).getId());
        Assert.assertEquals(12, users.get(5).getId());
    }

    @Test
    public void testExchangeCreateUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("your_access_token");

        CreateUserRequest request = CreateUserRequest.of("morpheus", "leader");
        HttpEntity<CreateUserRequest> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<CreateUserResponse> response = restTemplate.exchange(
                "https://reqres.in/api/users",
                HttpMethod.POST,
                httpEntity,
                CreateUserResponse.class
        );

        assertNotNull(response);
    }
}
