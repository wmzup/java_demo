package com.example.java_spring_boot.util;

import com.example.java_spring_boot.dto.request.CreateUserRequest;
import com.example.java_spring_boot.dto.request.CreateUserResponse;
import com.example.java_spring_boot.dto.response.CurrencyLayerResponse;
import com.example.java_spring_boot.dto.response.GetUserListResponse;
import com.example.java_spring_boot.dto.response.IpApiResponse;
import com.example.java_spring_boot.dto.response.SingleUserData;
import com.example.java_spring_boot.dto.response.SingleUserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateTest {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private IpApiClient ipApiClient;

    @Autowired
    CurrencyLayerClient currencyLayerClient;

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
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // 確認 response header
        assertNotNull(response.getHeaders().getContentType());
        assertEquals("application/json;charset=utf-8", response.getHeaders().getContentType().toString());

        // 確認 response body
        SingleUserResponse responseBody = response.getBody();
        assertNotNull(responseBody);

        SingleUserData data = responseBody.getData();
        assertEquals(1, data.getId());
        assertEquals("george.bluth@reqres.in", data.getEmail());
        assertEquals("George", data.getFirstName());
        assertEquals("Bluth", data.getLastName());
        assertEquals("https://reqres.in/img/faces/1-image.jpg", data.getAvatar());
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
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getJob(), response.getJob());
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

        assertEquals(6, users.size());
        assertEquals(7, users.get(0).getId());
        assertEquals(8, users.get(1).getId());
        assertEquals(9, users.get(2).getId());
        assertEquals(10, users.get(3).getId());
        assertEquals(11, users.get(4).getId());
        assertEquals(12, users.get(5).getId());
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

    @Test
    public void testIpApiClient_public() {
        IpApiResponse response = ipApiClient.getIpInfo("208.67.222.222");

        // 確認查詢是否有問題
        assertFalse(response.isError());
        assertNull(response.getReason());
        assertFalse(response.isReserved());

        // 確認 IP 所在地資訊
        assertEquals("San Francisco", response.getCity());
        assertEquals("USD", response.getCurrency());
        assertEquals(-122.397966, response.getLongitude(), 0);
        assertEquals(37.774778, response.getLatitude(), 0);
        assertEquals("-0700", response.getUtcOffset());
        assertEquals("+1", response.getCountryCallingCode());
    }

    @Test
    public void testIpApiClient_private() {
        IpApiResponse response = ipApiClient.getIpInfo("192.168.8.100");

        assertTrue(response.isError());
        assertEquals("Reserved IP Address", response.getReason());
        assertTrue(response.isReserved());
    }

    @Test
    public void testCurrencyLayerClient() {
        String sourceCurrency = "USD";
        List targetCurrencies = List.of("TWD", "JPY", "CNY", "EUR");
        CurrencyLayerResponse response = currencyLayerClient.getLiveExchangeRates(sourceCurrency, targetCurrencies);

        for (var target : targetCurrencies) {
            String pair = sourceCurrency + target;
            Double rate = response.getQuotes().get(pair);
            assertTrue(rate > 0);
        }
    }
}
