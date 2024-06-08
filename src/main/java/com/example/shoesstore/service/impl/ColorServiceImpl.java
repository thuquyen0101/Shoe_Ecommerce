package com.example.shoesstore.service.impl;

import com.example.shoesstore.constant.CategoryStatus;
import com.example.shoesstore.dto.request.ColorCreateRequest;
import com.example.shoesstore.dto.request.ColorUpdateRequest;
import com.example.shoesstore.dto.response.ColorResponse;
import com.example.shoesstore.entity.Category;
import com.example.shoesstore.entity.Color;
import com.example.shoesstore.entity.User;
import com.example.shoesstore.exception.AppException;
import com.example.shoesstore.exception.ErrorCode;
import com.example.shoesstore.mapper.ColorMapper;
import com.example.shoesstore.repository.ColorRepository;
import com.example.shoesstore.repository.UserRepository;
import com.example.shoesstore.service.ColorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ColorServiceImpl implements ColorService {

    ColorRepository colorRepository;
    ColorMapper colorMapper;
    UserRepository userRepository;

    @Override
    public Page<ColorResponse> getAllColors(int page, int size) {
        return colorRepository.findAll(PageRequest.of(page, size)).map(colorMapper::toResponse);
    }

    @Override
    public ColorResponse getColorById(long colorId) {
        return colorMapper.toResponse(colorRepository.findById(colorId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    @Override
    public ColorResponse createColor(ColorCreateRequest colorCreateRequest) {
        if (colorRepository.existsByName(colorCreateRequest.getName())) {
            throw new AppException(ErrorCode.COLOR_EXISTED);
        }
        Optional<User> user = Optional.ofNullable(userRepository.findById(colorCreateRequest.getCreatedBy()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        if (user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        Color color = colorMapper.toColor(colorCreateRequest);
        color.setStatus(CategoryStatus.ACTIVE);
        color.setCreatedBy(user.get().getName());
        return colorMapper.toResponse(colorRepository.save(color));
    }

    @Override
    public ColorResponse updateColor(long colorId, ColorUpdateRequest colorUpdateRequest) {
        Color color = colorRepository.findById(colorId).orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
        colorMapper.updateColor(color, colorUpdateRequest);
        color.setName(colorUpdateRequest.getColorName());
        return colorMapper.toResponse(colorRepository.save(color));
    }

    @Override
    public ColorResponse changeStatus(long colorId) {
        Color color = colorRepository.findById(colorId).orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
        if (color.getStatus().equals(CategoryStatus.ACTIVE)) {
            color.setStatus(CategoryStatus.NO_ACTIVE);
        } else {
            color.setStatus(CategoryStatus.ACTIVE);
        }
        return colorMapper.toResponse(colorRepository.save(color));
    }

    @Override
    public Page<ColorResponse> getColorsByNameContains(String name, int page, int size) {
        return colorRepository.findByNameContainsIgnoreCase(
                        name, PageRequest.of(page, size))
                .map(colorMapper::toResponse);
    }
}
