package com.example.shoesstore.mapper;

import com.example.shoesstore.dto.request.ShoeDetailCreateRequest;
import com.example.shoesstore.dto.request.ShoeDetailUpdateRequest;
import com.example.shoesstore.dto.response.ShoeDetailResponse;
import com.example.shoesstore.entity.ShoeDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ShoeDetailMapper {
    ShoeDetail mapToShoe(ShoeDetailCreateRequest request);

    @Mapping(target = "shoe", source = "shoeDetail.shoe")
    @Mapping(target = "category", source = "shoeDetail.category")
    @Mapping(target = "color", source = "shoeDetail.color")
    @Mapping(target = "size", source = "shoeDetail.size")
    @Mapping(target = "image", source = "shoeDetail.image")
    ShoeDetailResponse mapToResponse(ShoeDetail shoeDetail);


    void updateShoeDetail(@MappingTarget ShoeDetail shoeDetail, ShoeDetailUpdateRequest request);
}
