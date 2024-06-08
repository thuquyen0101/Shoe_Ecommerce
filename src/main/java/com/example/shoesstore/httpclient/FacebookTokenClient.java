package com.example.shoesstore.httpclient;

import com.example.shoesstore.dto.response.FacebookTokenValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "outbound-fb-identity", url = "https://graph.facebook.com")
public interface FacebookTokenClient {

    @GetMapping("/debug_token") // Specify the exact path
    FacebookTokenValidationResponse validateToken(
            @RequestParam("input_token") String inputToken,
            @RequestParam("access_token") String accessToken);
}