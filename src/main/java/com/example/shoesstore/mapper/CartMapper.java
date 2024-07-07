package com.example.shoesstore.mapper;

import com.example.shoesstore.dto.response.CartResponse;
import com.example.shoesstore.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
//    @Mapping(target = "user", source = "cart.users")
    @Mapping(target = "idUser", source = "cart.users.id")
//    @Mapping(target = "idCartDetails", source = "cart.cartDetails.id")
    CartResponse mapToResponse(Cart cart);

}
