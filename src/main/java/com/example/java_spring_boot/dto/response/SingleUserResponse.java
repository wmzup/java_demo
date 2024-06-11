package com.example.java_spring_boot.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SingleUserResponse {
    SingleUserData data;
    Support support;

    @Getter
    @Setter
    @NoArgsConstructor
    public class SingleUserData {
        @JsonProperty("id")
        private int id;
        @JsonProperty("email")
        private String email;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        @JsonProperty("avatar")
        private String avatar;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public class Support {
        @JsonProperty("url")
        private String url;
        @JsonProperty("text")
        private String text;
    }
}
