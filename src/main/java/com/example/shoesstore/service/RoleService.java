package com.example.shoesstore.service;

import com.example.shoesstore.dto.request.RoleCreateRequest;
import com.example.shoesstore.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleCreateRequest roleCreateRequest);

    List<RoleResponse> getAllRoles();

    void delete(String role);

}
