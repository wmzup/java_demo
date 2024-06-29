package com.example.java_spring_boot.util.ipapi;

import com.example.java_spring_boot.dto.response.IpInfoClientResponse;

public interface IpInfoClient {
    IpInfoClientResponse getIpInfo(String ipAddress);
}
