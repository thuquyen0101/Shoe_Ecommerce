package com.example.shoesstore.controller;

import com.example.shoesstore.constant.CodeStatusConstants;
import com.example.shoesstore.dto.request.CartDetailRequest;
import com.example.shoesstore.dto.response.ApiResponse;
import com.example.shoesstore.dto.response.CartResponse;
import com.example.shoesstore.service.CartDetailService;
import com.example.shoesstore.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart/{idUser}")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    CartDetailService cartDetailService;
    CartService cartService;

    @GetMapping
    public ApiResponse<CartResponse> getAll( @PathVariable Long idUser) {
        return ApiResponse.<CartResponse>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(cartService.getAll(idUser))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<CartResponse> addProductToCart(@RequestBody CartDetailRequest cartDetail, @PathVariable Long idUser) {
        return ApiResponse.<CartResponse>builder()
                .code(CodeStatusConstants.CREATED)
                .message("Add product success")
                .result(cartDetailService.addProductToCartDetail(cartDetail, idUser))
                .build();
    }

    @PutMapping("/{idCartDetail}/update")
    public ApiResponse<CartResponse> updateProductToCart(@RequestBody CartDetailRequest cartDetail, @PathVariable Long idCartDetail
            , @PathVariable Long idUser) {
        return ApiResponse.<CartResponse>builder()
                .code(CodeStatusConstants.UPDATE)
                .message("Update product success")
                .result(cartDetailService.updateCartDetail(idUser, idCartDetail, cartDetail))
                .build();
    }

    @PutMapping("/remove")
    public ApiResponse<CartResponse> removeProductFromCart(@PathVariable Long idUser, @RequestBody Long[] listIdShoeDetail) {
        return ApiResponse.<CartResponse>builder()
                .code(CodeStatusConstants.OK)
                .message("Remove product success")
                .result(cartService.removeShoeDetailFromCart(listIdShoeDetail, idUser))
                .build();
    }

    @PutMapping("/clear")
    public ApiResponse<CartResponse> clearCart(@PathVariable Long idUser) {
        return ApiResponse.<CartResponse>builder()
                .code(CodeStatusConstants.OK)
                .message("Clear cart success")
                .result(cartService.clearCart(idUser))
                .build();
    }
}
