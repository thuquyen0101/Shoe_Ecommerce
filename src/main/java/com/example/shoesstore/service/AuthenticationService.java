package com.example.shoesstore.service;

import com.example.shoesstore.dto.request.AuthenticationRequest;
import com.example.shoesstore.dto.request.IntrospectRequest;
import com.example.shoesstore.dto.request.LogoutRequest;
import com.example.shoesstore.dto.request.RefreshRequest;
import com.example.shoesstore.dto.response.AuthenticationResponse;
import com.example.shoesstore.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    AuthenticationResponse refreshToken(RefreshRequest refreshRequest) throws ParseException, JOSEException;

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException;
}
