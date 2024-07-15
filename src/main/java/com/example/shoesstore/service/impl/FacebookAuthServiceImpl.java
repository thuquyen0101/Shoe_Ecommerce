package com.example.shoesstore.service.impl;

import com.example.shoesstore.constant.PredefinedRole;
import com.example.shoesstore.dto.request.FacebookLoginRequest;
import com.example.shoesstore.dto.response.AuthenticationResponse;
import com.example.shoesstore.entity.Role;
import com.example.shoesstore.entity.User;
import com.example.shoesstore.httpclient.FacebookIdentityClient;
import com.example.shoesstore.httpclient.FacebookUserClient;
import com.example.shoesstore.repository.UserRepository;
import com.example.shoesstore.service.FacebookAuthService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.transaction.Transactional;
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
public class FacebookAuthServiceImpl implements FacebookAuthService {

    UserRepository userRepository;
    FacebookIdentityClient facebookIdentityClient;
    FacebookUserClient facebookUserClient;


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
    @Value("${facebook.client-id}")
    String CLIENT_ID;


    @NonFinal
    @Value("${facebook.client-secret}")
    String CLIENT_SECRET;

    @NonFinal
    @Value("${facebook.redirect-uri}")
    String REDIRECT_URI;

    final String TYPE = "authorization_code";

    @Override
    public AuthenticationResponse authenticateFacebookUser(String code) {
        var response = facebookIdentityClient.exchangeToken(FacebookLoginRequest.builder()
                .code(code)
                .client_id(CLIENT_ID)
                .client_secret(CLIENT_SECRET)
                .redirect_uri(REDIRECT_URI)
                .build());

        log.info("TOKEN RESPONSE {}", response);

        var userInfo = facebookUserClient.getUserInfo("id,name,email,picture", response.getAccess_token());

        Optional<User> existingUser = userRepository.findByFacebookAccount(userInfo.getId());

        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userToUpdate.setName(userInfo.getName());
            userToUpdate.setUrlAvatar(userInfo.getPicture().getData().getUrl());
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
                userToUpdate.setUrlAvatar(userInfo.getPicture().getData().getUrl());
                userToUpdate.setFacebookAccount(userInfo.getId());
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
                        .urlAvatar(userInfo.getPicture().getData().getUrl())
                        .facebookAccount(userInfo.getId())
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

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("nguyenhailong")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS)
                        .toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("userId", user.getId())
                .claim("name", user.getName())
                .claim("userName", user.getUsername())
                .claim("scope", buildScope())
                .build();

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
