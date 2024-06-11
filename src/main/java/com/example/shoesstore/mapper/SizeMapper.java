package com.example.shoesstore.mapper;

import com.example.shoesstore.dto.request.SizeCreateRequest;
import com.example.shoesstore.dto.request.SizeUpdateRequest;
import com.example.shoesstore.dto.response.SizeResponse;
import com.example.shoesstore.entity.Size;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SizeMapper {
    Size mapToSize(SizeCreateRequest request);

    SizeResponse mapToRespone(Size size);

    void updateSize(@MappingTarget Size size, SizeUpdateRequest request);
}
