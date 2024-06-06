package com.example.shoesstore.security;

public class EndPoints {


    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "api/v1/auth/login",
            "/api/v1/user/create",
            "/api/v1/auth/refresh",
            "/api/v1/auth/logout",
            "/api/v1/auth/introspect",
            "/api/v1/auth/facebook-login",
            "/api/v1/auth/google-login"
    };
}
