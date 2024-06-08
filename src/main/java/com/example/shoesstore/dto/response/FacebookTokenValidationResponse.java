package com.example.shoesstore.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data

public class FacebookTokenValidationResponse {
    private Data data;
    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        private String app_id;
        private String type;
        private String application;
        private long data_access_expires_at;
        private long expires_at;
        private Boolean is_valid;
        private List<String> scopes;
        private String user_id;
    }
}