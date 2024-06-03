package com.example.shoesstore.mapper;

import com.example.shoesstore.dto.request.UserCreateRequest;
import com.example.shoesstore.dto.response.UserResponse;
import com.example.shoesstore.entity.Role;
import com.example.shoesstore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreateRequest userCreateRequest);

    @Mapping(target = "roleNames", source = "roles", qualifiedByName = "mapRolesToStrings")
    UserResponse toResponse(User user);

    @Named("mapRolesToStrings")
    default List<String> mapRolesToStrings(List<Role> roles) {
        return roles.stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());
    }
}
