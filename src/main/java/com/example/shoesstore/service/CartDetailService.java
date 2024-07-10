package com.example.shoesstore.service;

import com.example.shoesstore.dto.request.CartDetailRequest;
import com.example.shoesstore.dto.response.CartResponse;

public interface CartDetailService {
    CartResponse addProductToCartDetail(CartDetailRequest request, Long idUser);
    CartResponse updateCartDetail(Long idUser, Long idCartDetail, CartDetailRequest request);

}
