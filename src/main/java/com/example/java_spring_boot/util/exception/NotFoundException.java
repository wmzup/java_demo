package com.example.java_spring_boot.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // http 404
public class NotFoundException extends RuntimeException {

    /*
    該類別繼承 RuntimeException，並使用 @ResponseStatus 標記，定義拋出例外時回應給呼叫方的 HTTP 狀態碼。
    最後是建構子，由於拋出例外後，Spring Boot 也會回傳類似回應主體（response body）的資料，讀者也可額外傳入字串訊息給請求方。
    * */

    public NotFoundException(String message) {
        super(message);
    }
}
