package com.example.java_spring_boot.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetUserListResponse {
    List<SingleUserData> data;
}
