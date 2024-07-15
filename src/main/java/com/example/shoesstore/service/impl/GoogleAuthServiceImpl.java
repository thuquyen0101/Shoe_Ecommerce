package com.example.shoesstore.service.impl;

import com.example.shoesstore.constant.PredefinedRole;
import com.example.shoesstore.dto.request.GoogleLoginRequest;
import com.example.shoesstore.dto.response.AuthenticationResponse;
import com.example.shoesstore.entity.Role;
import com.example.shoesstore.entity.User;
import com.example.shoesstore.httpclient.GoogleIdentityClient;
import com.example.shoesstore.httpclient.GoogleUserClient;
import com.example.shoesstore.repository.UserRepository;
import com.example.shoesstore.service.GoogleAuthService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class GoogleAuthServiceImpl implements GoogleAuthService {

    UserRepository userRepository;
    GoogleIdentityClient googleIdentityClient;
    GoogleUserClient googleUserClient;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @NonFinal
    @Value("${google.client-id}")
    protected String CLIENT_ID;

    @NonFinal
    @Value("${google.client-secret}")
    protected String CLIENT_SECRET;

    @NonFinal
    @Value("${google.redirect-uri}")
    protected String REDIRECT_URI;

    @NonFinal
    protected final String TYPE = "authorization_code";


    @Override
    public AuthenticationResponse authenticateGoogleUser(String code) {
        var response = googleIdentityClient.exchangeToken(GoogleLoginRequest.builder()
                .code(code)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .redirectUri(REDIRECT_URI)
                .grantType(TYPE)
                .build());

        log.info("TOKEN RESPONSE {}", response);

        var userInfo = googleUserClient.getUserInfo("json", response.getAccessToken());

        log.info("User Info {}", userInfo.getPicture());

        Optional<User> existingUser = userRepository.findByGoogleAccount(userInfo.getId());

        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userToUpdate.setName(userInfo.getName());
            userToUpdate.setUrlAvatar(userInfo.getPicture());
            userRepository.save(userToUpdate);

            var token = generateToken(userToUpdate);

            return AuthenticationResponse.builder()
                    .authenticated(true)
                    .token(token)
                    .build();
        } else {
            Optional<User> existingUserByEmail = userRepository.findByUsername(userInfo.getEmail());

            if (existingUserByEmail.isPresent()) {
                User userToUpdate = existingUserByEmail.get();
                userToUpdate.setName(userInfo.getName());
                userToUpdate.setUrlAvatar(userInfo.getPicture());
                userToUpdate.setGoogleAccount(userInfo.getId());
                userRepository.save(userToUpdate);

                var token = generateToken(userToUpdate);

                return AuthenticationResponse.builder()
                        .authenticated(true)
                        .token(token)
                        .build();
            } else {
                List<Role> roles = new ArrayList<>();
                roles.add(Role.builder().roleName(PredefinedRole.USER_ROLE).build());

                User newUser = User.builder()
                        .username(userInfo.getEmail())
                        .name(userInfo.getName())
                        .roles(roles)
                        .urlAvatar(userInfo.getPicture())
                        .googleAccount(userInfo.getId())
                        .build();

                userRepository.save(newUser);

                var token = generateToken(newUser);

                return AuthenticationResponse.builder()
                        .authenticated(true)
                        .token(token)
                        .build();
            }
        }
    }



    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet
                .Builder().subject(
                        user.getUsername())
                .issuer("nguyenhailong")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS)
                        .toEpochMilli())).jwtID(UUID.randomUUID().toString())
                .claim("userId", user.getId()).claim("name", user.getName())
                .claim("userName", user.getUsername()).claim("scope", buildScope()).build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope() {
        StringJoiner stringJoiner = new StringJoiner(" ");
        stringJoiner.add("ROLE_" + PredefinedRole.USER_ROLE);
        return stringJoiner.toString();
    }


}
