package com.example.shoesstore.service;

import com.example.shoesstore.dto.request.UserCreateRequest;
import com.example.shoesstore.dto.request.UserUpdateRequest;
import com.example.shoesstore.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserCreateRequest userCreateRequest);

    List<UserResponse> getAllUsers();

    void deleteUser(long userId);

    UserResponse getUserById(long userId);

    UserResponse updateUser(long userId, UserUpdateRequest userUpdateRequest);

}
