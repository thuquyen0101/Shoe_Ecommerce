package com.example.shoesstore.httpclient;

import com.example.shoesstore.dto.request.GoogleLoginRequest;
import com.example.shoesstore.dto.response.TokenGoogleResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "outbound-identity", url = "https://oauth2.googleapis.com")
public interface GoogleIdentityClient{
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenGoogleResponse exchangeToken(@QueryMap GoogleLoginRequest request);
}