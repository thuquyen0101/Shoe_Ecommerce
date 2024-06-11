package com.example.shoesstore.service.impl;

import com.example.shoesstore.constant.ShoeStatus;
import com.example.shoesstore.dto.request.ShoeCreateRequest;
import com.example.shoesstore.dto.request.ShoeUpdateRequest;
import com.example.shoesstore.dto.response.ShoeResponse;
import com.example.shoesstore.entity.Shoe;
import com.example.shoesstore.entity.User;
import com.example.shoesstore.exception.AppException;
import com.example.shoesstore.exception.ErrorCode;
import com.example.shoesstore.mapper.ShoeMapper;
import com.example.shoesstore.repository.ShoeRepository;
import com.example.shoesstore.repository.UserRepository;
import com.example.shoesstore.service.ShoeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ShoeServiceImpl implements ShoeService {
    ShoeRepository shoeRepository;
    ShoeMapper shoeMapper;
    UserRepository userRepository;

    @Override
    public ShoeResponse createShoe(ShoeCreateRequest request) {
        if (shoeRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.SHOE_EXISTED);
        }
        Optional<User> user = Optional.ofNullable(
                userRepository.findById(request.getCreatedBy())
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));

        if (user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        Shoe shoe = shoeMapper.mapToShoe(request);
        shoe.setStatus(ShoeStatus.ACTIVE);
        shoe.setCreateBy(user.get().getName());
        return shoeMapper.mapToResponse(shoeRepository.save(shoe));
    }

    @Override
    public ShoeResponse getShoeById(long id) {
        return shoeMapper.mapToResponse(shoeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SHOE_NOT_FOUND)));
    }

    @Override
    public Page<ShoeResponse> getAllShoe(int page, int size) {
        return shoeRepository.findAll(PageRequest.of(page, size))
                .map(shoe -> shoeMapper.mapToResponse(shoe));
    }

    @Override
    public ShoeResponse updateShoe(long id, ShoeUpdateRequest request) {
        Shoe shoe = shoeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SHOE_NOT_FOUND));
        shoeMapper.updateShoe(shoe, request);
        shoe.setDescription(request.getDescription());
        shoe.setName(request.getName());
        shoe.setPrice(request.getPrice());
        shoe.setDescription(request.getDescription());
        return shoeMapper.mapToResponse(shoeRepository.save(shoe));
    }

    @Override
    public ShoeResponse changeStatus(long id) {
        Shoe shoe = shoeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SHOE_NOT_FOUND));
        if (shoe.getStatus().equals(ShoeStatus.ACTIVE)) {
            shoe.setStatus(ShoeStatus.NO_ACTIVE);
        } else {
            shoe.setStatus(ShoeStatus.ACTIVE);
        }
        return shoeMapper.mapToResponse(shoeRepository.save(shoe));
    }

    @Override
    public Page<ShoeResponse> searchByNameOrStatus(String name, String statusStr, int page, int size) {
        String keyword;
        Integer status;
        if (name.trim().isEmpty() || name.trim() == null) {
            keyword = "";
        } else {
            keyword = name.trim();
        }

        if (statusStr.trim().isEmpty() || statusStr.trim() == null) {
            return shoeRepository.findShoesByNameContainsIgnoreCase("%" + keyword + "%", PageRequest.of(page, size))
                    .map(shoe -> shoeMapper.mapToResponse(shoe));
        } else {
            status = Integer.parseInt(statusStr.trim());
        }

        return shoeRepository.findShoesByStatusAndNameContainsIgnoreCase("%" + keyword + "%", status, PageRequest.of(page, size))
                .map(shoe -> shoeMapper.mapToResponse(shoe));
    }

}
