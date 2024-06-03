package com.example.shoesstore.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(404, "User not found", HttpStatus.NOT_FOUND),
    USER_EXISTED(400, "User existed", HttpStatus.BAD_REQUEST),
    INVALID_KEY(400, "Invalid key", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(401, "Unauthorized Error", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(401, "Unauthenticated Error", HttpStatus.UNAUTHORIZED),
    ;


    private final int code;

    private final String message;

    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}
