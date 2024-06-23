package com.example.shoesstore.dto.request;

import com.example.shoesstore.exception.AppException;
import com.example.shoesstore.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ShoeDetailSearchRequest {
    Long idShoe;
    Long idCategory;
    Long idColor;
    Long idSize;
    Long price;
}
