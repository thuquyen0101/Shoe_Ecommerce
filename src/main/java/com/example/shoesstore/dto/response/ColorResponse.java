package com.example.shoesstore.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ColorResponse {

    private Long id;

    private String code;

    private String name;

    private Integer status;

    private String createdBy;
}
