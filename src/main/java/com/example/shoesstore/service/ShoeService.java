package com.example.shoesstore.service;

import com.example.shoesstore.dto.request.ShoeCreateRequest;
import com.example.shoesstore.dto.request.ShoeUpdateRequest;
import com.example.shoesstore.dto.response.ShoeResponse;
import org.springframework.data.domain.Page;

public interface ShoeService {
    ShoeResponse createShoe(ShoeCreateRequest request);

    ShoeResponse getShoeById(long id);

    Page<ShoeResponse> getAllShoe(int page, int size);

    ShoeResponse updateShoe(long id, ShoeUpdateRequest request);

    ShoeResponse changeStatus(long id);

    Page<ShoeResponse> searchByNameOrStatus(String name, String status, int page, int size);

}
