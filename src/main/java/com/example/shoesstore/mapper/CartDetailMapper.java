package com.example.shoesstore.mapper;

import com.example.shoesstore.dto.request.CartDetailRequest;
import com.example.shoesstore.dto.response.CartDetailResponse;
import com.example.shoesstore.entity.CartDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartDetailMapper {

    @Mapping(target = "idShoeDetail", source = "cartDetail.shoeDetail.id")
    @Mapping(target = "quantity", source = "cartDetail.quantity")
    @Mapping(target = "id", source = "cartDetail.id")
    CartDetailResponse mapToResponse(CartDetail cartDetail);
}
