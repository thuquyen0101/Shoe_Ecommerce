package com.example.shoesstore.controller;

import com.example.shoesstore.constant.CodeStatusConstants;
import com.example.shoesstore.dto.request.RoleCreateRequest;
import com.example.shoesstore.dto.response.ApiResponse;
import com.example.shoesstore.dto.response.RoleResponse;
import com.example.shoesstore.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {

    RoleService roleService;

    @PostMapping("/create")
    ApiResponse<RoleResponse> createRole(@RequestBody RoleCreateRequest roleCreateRequest) {
        return ApiResponse.<RoleResponse>builder()
                .code(CodeStatusConstants.CREATED)
                .message("Success")
                .result(roleService.createRole(roleCreateRequest))
                .build();
    }

    @GetMapping("/all")
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .code(CodeStatusConstants.OK)
                .result(roleService.getAllRoles())
                .build();
    }

    @DeleteMapping("/delete/{role}")
    ApiResponse<String> delete(@PathVariable String role) {
        roleService.delete(role);
        return ApiResponse.<String>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .build();
    }

}
