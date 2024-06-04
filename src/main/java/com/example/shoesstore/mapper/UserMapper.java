package com.example.shoesstore.mapper;

import com.example.shoesstore.dto.request.UserCreateRequest;
import com.example.shoesstore.dto.request.UserUpdateRequest;
import com.example.shoesstore.dto.response.UserResponse;
import com.example.shoesstore.entity.Role;
import com.example.shoesstore.entity.User;
import org.mapstruct.*;

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

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "orders", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
