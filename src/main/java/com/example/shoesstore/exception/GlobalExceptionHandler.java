package com.example.shoesstore.exception;

import com.example.shoesstore.dto.response.ApiResponse;
import com.example.shoesstore.dto.response.FieldErrorDetail;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        log.error("Exception: ", exception);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }


    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handleRunTimeException(RuntimeException e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.UNAUTHORIZED.getCode());
        apiResponse.setMessage(ErrorCode.UNAUTHORIZED.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }


    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }


    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse.builder().code(errorCode.getCode()).message(errorCode.getMessage()).build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        StringBuilder validationMessages = new StringBuilder();
        List<FieldErrorDetail> errorDetails = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            String enumKey = fieldError.getDefaultMessage();
            ErrorCode errorCode = ErrorCode.INVALID_KEY;
            String validationMessage = null;
            Map<String, Object> attributes = null;

            try {
                errorCode = ErrorCode.valueOf(enumKey);

                var constraintViolation = exception.getBindingResult().getAllErrors().stream().map(error -> error.unwrap(ConstraintViolation.class)).filter(cv -> cv.getPropertyPath().toString().equals(fieldError.getField())).findFirst().orElse(null);

                if (constraintViolation != null) {
                    attributes = constraintViolation.getConstraintDescriptor().getAttributes();
                    validationMessage = constraintViolation.getMessage();
                }

            } catch (IllegalArgumentException ignored) {
                validationMessage = enumKey;
            }

            if (validationMessage != null) {
                validationMessages.append(validationMessage).append("; ");
                errorDetails.add(new FieldErrorDetail(fieldError.getField(), validationMessage));
            } else {
                validationMessages.append(errorCode.getMessage()).append("; ");
                errorDetails.add(new FieldErrorDetail(fieldError.getField(), errorCode.getMessage()));
            }
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.INVALID_KEY.getCode());
        apiResponse.setMessage(ErrorCode.INVALID_KEY.getMessage());
        apiResponse.setErrors(errorDetails);

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = JwtException.class)
    ResponseEntity<ApiResponse> handleJwtException(JwtException e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.JWT_ERROR.getCode());
        apiResponse.setMessage(ErrorCode.JWT_ERROR.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }

    @ExceptionHandler(value = AuthenticationServiceException.class)
    ResponseEntity<ApiResponse> handleAuthenticationServiceException(AuthenticationServiceException e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.AUTHENTICATION_FAILED.getCode());
        apiResponse.setMessage(ErrorCode.AUTHENTICATION_FAILED.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }




}



