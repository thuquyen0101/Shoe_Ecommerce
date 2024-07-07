package com.example.shoesstore.service;

import com.example.shoesstore.dto.response.CartResponse;

public interface CartService {
    CartResponse removeShoeDetailFromCart(Long[] ids, Long idUser);
    CartResponse clearCart(Long idUser);
    CartResponse getAll(Long idUser);
}
