package com.example.shoesstore.service;

import com.example.shoesstore.dto.request.ColorCreateRequest;
import com.example.shoesstore.dto.request.ColorUpdateRequest;
import com.example.shoesstore.dto.response.ColorResponse;
import org.springframework.data.domain.Page;

public interface ColorService {

    Page<ColorResponse> getAllColors(int page, int size);

    ColorResponse getColorById(long colorId);

    ColorResponse createColor(ColorCreateRequest colorCreateRequest);

    ColorResponse updateColor(long colorId, ColorUpdateRequest colorUpdateRequest);

    ColorResponse changeStatus(long colorId);

    Page<ColorResponse> getColorsByNameContains(String name, int page, int size);

}
