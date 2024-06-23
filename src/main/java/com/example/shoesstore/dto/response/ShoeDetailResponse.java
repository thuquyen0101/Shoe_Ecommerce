package com.example.shoesstore.dto.response;

import com.example.shoesstore.entity.Category;
import com.example.shoesstore.entity.Color;
import com.example.shoesstore.entity.Shoe;
import com.example.shoesstore.entity.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ShoeDetailResponse {
    Long id;
    Integer quantity;
    Long price;
    Integer status;
    ShoeResponse shoe;
    CategoryResponse category;
    ColorResponse color;
    SizeResponse size;
    ImageResponse image;
    String createdBy;
}
