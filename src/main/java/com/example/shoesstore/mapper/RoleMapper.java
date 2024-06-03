package com.example.shoesstore.mapper;

import com.example.shoesstore.dto.request.RoleCreateRequest;
import com.example.shoesstore.dto.response.RoleResponse;
import com.example.shoesstore.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toRole(RoleCreateRequest roleCreateRequest);

    RoleResponse toResponse(Role role);
}
