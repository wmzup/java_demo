package com.example.java_spring_boot.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IpApiResponse {
    // 查詢失敗會得到的欄位
    private boolean error;
    private String reason;
    private boolean reserved; // true 代表私有 IP

    // IP 所在地資訊
    private String city;
    private String currency;
    private Double latitude;
    private Double longitude;

    @JsonProperty("utc_offset")
    private String utcOffset;
    @JsonProperty("country_calling_code")
    private String countryCallingCode;

}
