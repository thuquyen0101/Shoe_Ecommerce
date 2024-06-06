package com.example.shoesstore.dto.response;

import lombok.Data;

@Data
public class FacebookTokenValidationResponse {
    private Data data;

    @lombok.Data
    public static class Data {
        private boolean isValid;
        private String user_id;
    }
}