package com.example.shoesstore.service.impl;

import com.example.shoesstore.constant.PredefinedRole;
import com.example.shoesstore.dto.request.FacebookLoginRequest;
import com.example.shoesstore.dto.response.AuthenticationResponse;
import com.example.shoesstore.dto.response.FacebookTokenValidationResponse;
import com.example.shoesstore.entity.User;
import com.example.shoesstore.exception.AppException;
import com.example.shoesstore.exception.ErrorCode;
import com.example.shoesstore.repository.UserRepository;
import com.example.shoesstore.service.FacebookAuthService;
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
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class FacebookAuthServiceImpl implements FacebookAuthService {

    UserRepository userRepository;
    WebClient.Builder webClientBuilder;

    @Value("${facebook.app.access.token}")
    @NonFinal
    protected String APP_ACCESS_TOKEN;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION ;



    @Override
    public AuthenticationResponse authenticateFacebookUser(FacebookLoginRequest facebookLoginRequest) {
        try {
            String accessToken = facebookLoginRequest.getAccessToken();
            String userID = facebookLoginRequest.getUserID();

            String url = "https://graph.facebook.com/debug_token?input_token=" + accessToken + "&access_token=" + APP_ACCESS_TOKEN;

            FacebookTokenValidationResponse response = webClientBuilder.build()
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(FacebookTokenValidationResponse.class)
                    .block();
            log.info("urt {}", url);

            if (response == null || !response.getData().isValid()) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }

            Optional<User> optionalUser = userRepository.findByUsername(facebookLoginRequest.getProfile().getEmail());
            User user;
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            } else {
                user = new User();
                user.setUsername(facebookLoginRequest.getProfile().getEmail());
                user.setName(facebookLoginRequest.getProfile().getName());
                user.setFb_account(userID);
                userRepository.save(user);
            }

            String token = generateToken(user);

            return AuthenticationResponse.builder().token(token).authenticated(true).build();
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
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
