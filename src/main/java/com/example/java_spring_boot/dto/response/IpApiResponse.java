package com.example.java_spring_boot.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IpApiResponse implements IpInfoClientResponse {
    private boolean error;
    private String reason;
    private boolean reserved; // true 代表私有 IP

    private String city;
    private String currency;
    private Double latitude;
    private Double longitude;

    @JsonProperty("utc_offset")
    private String utcOffset;

    @JsonProperty("country_calling_code")
    private String callingCode;

    @Override
    public String getErrorReason() {
        if (!error) {
            return null;
        } else if (reserved) {
            return "Reserved IP Address";
        } else {
            return reason;
        }
    }
}
