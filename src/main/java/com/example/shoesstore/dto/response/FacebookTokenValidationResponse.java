package com.example.shoesstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacebookTokenValidationResponse {
    private boolean isValid;
    private String user_id;
}