package com.example.shoesstore.service;

import com.example.shoesstore.dto.request.SizeCreateRequest;
import com.example.shoesstore.dto.request.SizeUpdateRequest;
import com.example.shoesstore.dto.response.SizeResponse;
import org.springframework.data.domain.Page;

public interface SizeService {
    Page<SizeResponse> getAllSize(int page, int size);

    SizeResponse createSize(SizeCreateRequest createRequest);

    SizeResponse getSizeById(long id);

    SizeResponse updateSize(long id, SizeUpdateRequest request);

    SizeResponse updateStatus(long id);

    Page<SizeResponse> filterSize(int status, int page, int size);

}
