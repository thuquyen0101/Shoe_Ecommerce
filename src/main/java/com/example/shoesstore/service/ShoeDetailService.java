package com.example.shoesstore.service;

import com.example.shoesstore.dto.request.ShoeDetailCreateRequest;
import com.example.shoesstore.dto.request.ShoeDetailSearchRequest;
import com.example.shoesstore.dto.request.ShoeDetailUpdateRequest;
import com.example.shoesstore.dto.response.ShoeDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface ShoeDetailService {
    ShoeDetailResponse createShoeDetail(ShoeDetailCreateRequest request) throws IOException;
    ShoeDetailResponse getShoeDetailById(long idShoeDetail);
    Page<ShoeDetailResponse> getAllShoeDetail(int page, int size);
    ShoeDetailResponse updateShoeDetail(long id, ShoeDetailUpdateRequest request) throws IOException;
    Page<ShoeDetailResponse> filter(ShoeDetailSearchRequest request, Pageable pageable);

    ShoeDetailResponse changeStatus(long idShoeDetail);

}
