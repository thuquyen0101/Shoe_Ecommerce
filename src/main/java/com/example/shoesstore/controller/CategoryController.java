package com.example.shoesstore.controller;

import com.example.shoesstore.constant.CodeStatusConstants;
import com.example.shoesstore.dto.request.CategoryCreateRequest;
import com.example.shoesstore.dto.request.CategoryUpdateRequest;
import com.example.shoesstore.dto.response.ApiResponse;
import com.example.shoesstore.dto.response.CategoryResponse;
import com.example.shoesstore.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {

    CategoryService categoryService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<CategoryResponse> createUser(@RequestBody @Valid CategoryCreateRequest categoryCreateRequest) {
        return ApiResponse.<CategoryResponse>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(categoryService.createCategory(categoryCreateRequest))
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<Page<CategoryResponse>> getAllCategories(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        return ApiResponse.<Page<CategoryResponse>>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(categoryService.getAllCategories(page, size))
                .build();
    }


    @GetMapping("getCategory/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<CategoryResponse> getUserById(@PathVariable long userId) {
        return ApiResponse.<CategoryResponse>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(categoryService.getCategoryById(userId))
                .build();
    }

    @PutMapping("/update/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<CategoryResponse> updateUser(@PathVariable long categoryId, @RequestBody @Valid CategoryUpdateRequest categoryUpdateRequest) {
        return ApiResponse.<CategoryResponse>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(categoryService.updateCategory(categoryId, categoryUpdateRequest))
                .build();
    }

    @PutMapping("/changeStatus/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<CategoryResponse> changeStatus(@PathVariable long categoryId) {
        return ApiResponse.<CategoryResponse>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(categoryService.changeStatus(categoryId))
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<Page<CategoryResponse>> getCategoriesByNameContains(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        return ApiResponse.<Page<CategoryResponse>>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(categoryService.getCategoriesByNameContains(name, page, size))
                .build();
    }

}
