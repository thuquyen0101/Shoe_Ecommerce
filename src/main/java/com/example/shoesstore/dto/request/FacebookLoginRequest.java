package com.example.shoesstore.dto.request;

import com.example.shoesstore.dto.response.FacebookProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacebookLoginRequest {
    private String accessToken;
    private String userID;
    private FacebookProfileResponse profile;
}
