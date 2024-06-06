package com.example.shoesstore.service;

import com.example.shoesstore.dto.response.AuthenticationResponse;
import com.example.shoesstore.exception.AppException;

public interface GoogleAuthService {

    AuthenticationResponse authenticateGoogleUser(String code) throws AppException;
}
