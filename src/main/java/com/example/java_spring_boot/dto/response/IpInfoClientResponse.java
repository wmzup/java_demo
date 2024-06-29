package com.example.java_spring_boot.dto.response;

public interface IpInfoClientResponse {
    String getCity();
    String getCurrency();
    Double getLatitude();
    Double getLongitude();
    String getUtcOffset();
    String getCallingCode();
    String getErrorReason();
}
