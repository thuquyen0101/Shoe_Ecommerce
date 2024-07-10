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
    CATEGORY_EXISTED(400, "Category existed", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN_GOOGLE(400, "Invalid token google", HttpStatus.BAD_REQUEST),
    AUTHENTICATION_FAILED(401, "Authentication failed", HttpStatus.UNAUTHORIZED),
    UNCATEGORIZED_EXCEPTION(400, "Uncategorized exception", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(400, "Invalid token", HttpStatus.BAD_REQUEST),
    JWT_ERROR(400, "JWT error", HttpStatus.BAD_REQUEST),
    INVALID_JWT(400, "Invalid JWT", HttpStatus.BAD_REQUEST),
    COLOR_EXISTED(400, "Color existed", HttpStatus.BAD_REQUEST),
    SIZE_EXISTED(400, "Size existed", HttpStatus.BAD_REQUEST),
    SIZE_NOT_FOUND(404, "Size does not found", HttpStatus.NOT_FOUND),
    SHOE_EXISTED(400, "Shoe existed", HttpStatus.BAD_REQUEST),
    SHOE_NOT_FOUND(404, "Shoe does not found", HttpStatus.NOT_FOUND),
    IMAGE_NOT_FOUND(404 , "Image not found" , HttpStatus.NOT_FOUND),
    SHOEDETAIL_NOT_FOUND(404, "Shoe detail does not found", HttpStatus.NOT_FOUND),
    OUT_OF_STOCK(404, "Product is out of stock", HttpStatus.BAD_REQUEST),
    CART_ITEM_NOT_FOUND(404, "The item of this cart is not found", HttpStatus.BAD_REQUEST),

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
