package com.example.shoesstore.service.impl;

import com.example.shoesstore.dto.request.RoleCreateRequest;
import com.example.shoesstore.dto.response.RoleResponse;
import com.example.shoesstore.mapper.RoleMapper;
import com.example.shoesstore.repository.RoleRepository;
import com.example.shoesstore.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoleServiceImpl implements RoleService {

    RoleMapper roleMapper;
    RoleRepository roleRepository;

    @Override
    public RoleResponse createRole(RoleCreateRequest roleCreateRequest) {
        var role = roleMapper.toRole(roleCreateRequest);
        role = roleRepository.save(role);
        return roleMapper.toResponse(role);
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public void delete(String role) {
        roleRepository.deleteByRoleName(role);
    }
}
