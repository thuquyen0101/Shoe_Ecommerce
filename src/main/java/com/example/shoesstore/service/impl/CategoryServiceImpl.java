package com.example.shoesstore.service.impl;

import com.example.shoesstore.constant.CategoryStatus;
import com.example.shoesstore.dto.request.CategoryCreateRequest;
import com.example.shoesstore.dto.request.CategoryUpdateRequest;
import com.example.shoesstore.dto.response.CategoryResponse;
import com.example.shoesstore.entity.Category;
import com.example.shoesstore.entity.User;
import com.example.shoesstore.exception.AppException;
import com.example.shoesstore.exception.ErrorCode;
import com.example.shoesstore.mapper.CategoryMapper;
import com.example.shoesstore.repository.CategoryRepository;
import com.example.shoesstore.repository.UserRepository;
import com.example.shoesstore.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    UserRepository userRepository;

    @Override
    public Page<CategoryResponse> getAllCategories(int page, int size) {
        return categoryRepository.findAll(PageRequest.of(page, size)).map(categoryMapper::toResponse);
    }

    @Override
    public CategoryResponse getCategoryById(long categoryId) {
        return categoryMapper.toResponse(categoryRepository.findById(categoryId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest categoryCreateRequest) {
        if (categoryRepository.existsByCategoryName(categoryCreateRequest.getCategoryName())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        Optional<User> user = Optional.ofNullable(userRepository.findById(categoryCreateRequest.getCreatedBy()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        if (user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        Category category = categoryMapper.toCategory(categoryCreateRequest);
        category.setStatus(CategoryStatus.ACTIVE);
        category.setCreatedBy(user.get().getName());
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(long categoryId, CategoryUpdateRequest categoryUpdateRequest) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
        categoryMapper.updateCategory(category, categoryUpdateRequest);
        category.setCategoryName(categoryUpdateRequest.getCategoryName());
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse changeStatus(long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
        if (category.getStatus().equals(CategoryStatus.ACTIVE)) {
            category.setStatus(CategoryStatus.NO_ACTIVE);
        } else {
            category.setStatus(CategoryStatus.ACTIVE);
        }
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public Page<CategoryResponse> getCategoriesByNameContains(String name, int page, int size) {
        return categoryRepository.findByCategoryNameContainingIgnoreCase(
                name, PageRequest.of(page, size))
                .map(categoryMapper::toResponse);
    }

}
