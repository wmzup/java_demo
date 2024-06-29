package com.example.java_spring_boot.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Support {
    @JsonProperty("url")
    private String url;
    @JsonProperty("text")
    private String text;
}
