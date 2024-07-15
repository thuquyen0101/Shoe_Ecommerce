package com.example.shoesstore.controller;

import com.example.shoesstore.constant.CodeStatusConstants;
import com.example.shoesstore.dto.request.*;
import com.example.shoesstore.dto.response.ApiResponse;
import com.example.shoesstore.dto.response.AuthenticationResponse;
import com.example.shoesstore.dto.response.IntrospectResponse;
import com.example.shoesstore.service.AuthenticationService;
import com.example.shoesstore.service.FacebookAuthService;
import com.example.shoesstore.service.GoogleAuthService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;
    FacebookAuthService facebookAuthService;
    GoogleAuthService googleAuthService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(CodeStatusConstants.OK)
                .result(result).build();
    }


    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .code(CodeStatusConstants.OK)
                .result(result)
                .build();
    }


    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .code(CodeStatusConstants.OK)
                .message("Logout success")
                .build();
    }


    @PostMapping("/facebook-login")
    public ApiResponse<AuthenticationResponse> facebookLogin(@RequestBody FacebookLoginRequest facebookLoginRequest) {
        AuthenticationResponse response = facebookAuthService.authenticateFacebookUser(facebookLoginRequest.getCode());
        return ApiResponse.<AuthenticationResponse>builder()
                .code(CodeStatusConstants.OK)
                .result(response)
                .build();
    }

    @PostMapping("/google-login")
    ApiResponse<AuthenticationResponse> outboundAuthenticate(
            @RequestParam("code") String code
    ) {
        var result = googleAuthService.authenticateGoogleUser(code);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }


}
