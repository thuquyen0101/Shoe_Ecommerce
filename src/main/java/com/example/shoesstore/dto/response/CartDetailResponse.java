package com.example.shoesstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CartDetailResponse {
    Long id;
    Integer quantity;
//    ShoeDetailResponse shoeDetail;
//    CartResponse cart;
    Long idShoeDetail;

}
