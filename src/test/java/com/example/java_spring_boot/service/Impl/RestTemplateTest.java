package com.example.java_spring_boot.service.Impl;

import com.example.java_spring_boot.dto.request.CreateUserRequest;
import com.example.java_spring_boot.dto.request.CreateUserResponse;
import com.example.java_spring_boot.dto.response.SingleUserResponse;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.bson.assertions.Assertions.assertNotNull;

@SpringBootTest
public class RestTemplateTest {

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testGetSingleUser()
    {
        // 發送請求並取得回應
        ResponseEntity<SingleUserResponse> response = restTemplate.getForEntity(
                "https://reqres.in/api/users/1",
                SingleUserResponse.class
        );

        // 確認 HTTP 狀態碼
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // 確認 response header
        assertNotNull(response.getHeaders().getContentType());
        Assert.assertEquals("application/json;charset=utf-8", response.getHeaders().getContentType().toString());

        // 確認 response body
        SingleUserResponse responseBody = response.getBody();
        assertNotNull(responseBody);

        SingleUserResponse.SingleUserData data = responseBody.getData();
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
}
