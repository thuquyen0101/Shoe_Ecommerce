package com.example.shoesstore.mapper;

import com.example.shoesstore.dto.request.ColorCreateRequest;
import com.example.shoesstore.dto.request.ColorUpdateRequest;
import com.example.shoesstore.dto.response.ColorResponse;
import com.example.shoesstore.entity.Color;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    Color toColor(ColorCreateRequest colorCreateRequest);

    ColorResponse toResponse(Color color);


    void updateColor(@MappingTarget Color color, ColorUpdateRequest colorUpdateRequest);

}
