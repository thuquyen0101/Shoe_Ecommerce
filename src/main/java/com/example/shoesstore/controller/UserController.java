package com.example.shoesstore.controller;

import com.example.shoesstore.constant.CodeStatusConstants;
import com.example.shoesstore.dto.request.UserCreateRequest;
import com.example.shoesstore.dto.request.UserUpdateRequest;
import com.example.shoesstore.dto.response.ApiResponse;
import com.example.shoesstore.dto.response.UserResponse;
import com.example.shoesstore.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        return ApiResponse.<UserResponse>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(userService.createUser(userCreateRequest))
                .build();
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(userService.getAllUsers())
                .build();
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .build();
    }

    @GetMapping("getUser/{userId}")
    public ApiResponse<UserResponse> getUserById(@PathVariable long userId) {
        return ApiResponse.<UserResponse>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(userService.getUserById(userId))
                .build();
    }

    @PutMapping("update/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<UserResponse> updateUser(@PathVariable long userId, @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }
}
