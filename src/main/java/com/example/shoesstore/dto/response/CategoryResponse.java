package com.example.shoesstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    private Long id;

    private String code;

    private String categoryName;

    private String createdBy;

    private Date createdAt;

    private Integer status;

}
