package com.example.shoesstore.service;

import com.example.shoesstore.dto.request.CategoryCreateRequest;
import com.example.shoesstore.dto.request.CategoryUpdateRequest;
import com.example.shoesstore.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {

    Page<CategoryResponse> getAllCategories(int page, int size);

    CategoryResponse getCategoryById(long categoryId);

    CategoryResponse createCategory(CategoryCreateRequest categoryCreateRequest);

    CategoryResponse updateCategory(long categoryId, CategoryUpdateRequest categoryUpdateRequest);

    CategoryResponse changeStatus(long categoryId);

    Page<CategoryResponse> getCategoriesByNameContains(String name, int page, int size);
}
