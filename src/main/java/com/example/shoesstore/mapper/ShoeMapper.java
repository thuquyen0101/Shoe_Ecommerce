package com.example.shoesstore.mapper;

import com.example.shoesstore.dto.request.ShoeCreateRequest;
import com.example.shoesstore.dto.request.ShoeUpdateRequest;
import com.example.shoesstore.dto.response.ShoeResponse;
import com.example.shoesstore.entity.Shoe;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ShoeMapper {
    Shoe mapToShoe(ShoeCreateRequest request);

    ShoeResponse mapToResponse(Shoe shoe);

    void updateShoe(@MappingTarget Shoe shoe, ShoeUpdateRequest request);

}
