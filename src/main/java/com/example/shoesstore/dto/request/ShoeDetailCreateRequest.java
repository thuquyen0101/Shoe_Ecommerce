package com.example.shoesstore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ShoeDetailCreateRequest {
    Integer quantity;
    Long price;
    Long idShoe;
    Long idCategory;
    Long idColor;
    Long idSize;
    MultipartFile file;
    Long createdBy;
}
