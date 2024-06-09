package com.example.shoesstore.mapper;

import com.example.shoesstore.dto.request.CategoryCreateRequest;
import com.example.shoesstore.dto.request.CategoryUpdateRequest;
import com.example.shoesstore.dto.response.CategoryResponse;
import com.example.shoesstore.entity.Category;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryCreateRequest categoryCreateRequest);

    CategoryResponse toResponse(Category category);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "shoeDetails", ignore = true)
    void updateCategory(@MappingTarget Category category, CategoryUpdateRequest categoryUpdateRequest);
}
