package com.example.shoesstore.service.impl;

import com.example.shoesstore.constant.SizeStatus;
import com.example.shoesstore.dto.request.SizeCreateRequest;
import com.example.shoesstore.dto.request.SizeUpdateRequest;
import com.example.shoesstore.dto.response.SizeResponse;
import com.example.shoesstore.entity.Size;
import com.example.shoesstore.entity.User;
import com.example.shoesstore.exception.AppException;
import com.example.shoesstore.exception.ErrorCode;
import com.example.shoesstore.mapper.SizeMapper;
import com.example.shoesstore.repository.SizeRepository;
import com.example.shoesstore.repository.UserRepository;
import com.example.shoesstore.service.SizeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SizeServiceImpl implements SizeService {

    SizeRepository sizeRepository;
    SizeMapper sizeMapper;
    UserRepository userRepository;

    @Override
    public Page<SizeResponse> getAllSize(int page, int size) {
        return sizeRepository.findAll(PageRequest.of(page, size))
                .map(sizeShoe -> sizeMapper.mapToRespone(sizeShoe));
    }

    @Override
    public SizeResponse createSize(SizeCreateRequest request) {
        if (sizeRepository.existsSizeBySizeLength(request.getSizeLength())) {
            log.info("request has length {}", request.getSizeLength());
            throw new AppException(ErrorCode.SIZE_EXISTED);
        }

        Optional<User> user = Optional.ofNullable(
                userRepository.findById(request.getCreatedBy())
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));

        if (user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        Size size = sizeMapper.mapToSize(request);
        size.setStatus(SizeStatus.ACTIVE);
        size.setCreatedBy(user.get().getName());
        log.info("length of size {}", request.getSizeLength());
        return sizeMapper.mapToRespone(sizeRepository.save(size));
    }

    @Override
    public SizeResponse getSizeById(long id) {
        return sizeMapper.mapToRespone(sizeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND)));
    }

    @Override
    public SizeResponse updateSize(long id, SizeUpdateRequest request) {
        Size size = sizeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));
        sizeMapper.updateSize(size, request);
        size.setSizeLength(request.getSizeLength());
        return sizeMapper.mapToRespone(sizeRepository.save(size));
    }

    @Override
    public SizeResponse updateStatus(long id) {
        Size size = sizeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));
        if (size.getStatus().equals(SizeStatus.ACTIVE)) {
            size.setStatus(SizeStatus.NO_ACTIVE);
        } else {
            size.setStatus(SizeStatus.ACTIVE);
        }
        return sizeMapper.mapToRespone(sizeRepository.save(size));
    }

    @Override
    public Page<SizeResponse> filterSize(int status, int page, int size) {
        return sizeRepository.findSizesByStatus(status, PageRequest.of(page, size))
                .map(sizeShoe -> sizeMapper.mapToRespone(sizeShoe));
    }
}
