package com.example.shoesstore.service;

import com.example.shoesstore.dto.request.FacebookLoginRequest;
import com.example.shoesstore.dto.response.AuthenticationResponse;

public interface FacebookAuthService {
    AuthenticationResponse authenticateFacebookUser(FacebookLoginRequest facebookLoginRequest);
}
