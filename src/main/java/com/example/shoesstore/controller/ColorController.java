package com.example.shoesstore.controller;

import com.example.shoesstore.constant.CodeStatusConstants;
import com.example.shoesstore.dto.request.CategoryCreateRequest;
import com.example.shoesstore.dto.request.CategoryUpdateRequest;
import com.example.shoesstore.dto.request.ColorCreateRequest;
import com.example.shoesstore.dto.request.ColorUpdateRequest;
import com.example.shoesstore.dto.response.ApiResponse;
import com.example.shoesstore.dto.response.CategoryResponse;
import com.example.shoesstore.dto.response.ColorResponse;
import com.example.shoesstore.service.ColorService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/color")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ColorController {

    ColorService colorService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ColorResponse> createColor(@RequestBody @Valid ColorCreateRequest colorCreateRequest) {
        return ApiResponse.<ColorResponse>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(colorService.createColor(colorCreateRequest))
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<Page<ColorResponse>> getAllColor(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        return ApiResponse.<Page<ColorResponse>>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(colorService.getAllColors(page, size))
                .build();
    }


    @GetMapping("getColorById/{colorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ColorResponse> getColorById(@PathVariable long colorId) {
        return ApiResponse.<ColorResponse>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(colorService.getColorById(colorId))
                .build();
    }

    @PutMapping("/update/{colorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ColorResponse> updateUser(@PathVariable long colorId, @RequestBody @Valid ColorUpdateRequest colorUpdateRequest) {
        return ApiResponse.<ColorResponse>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(colorService.updateColor(colorId, colorUpdateRequest))
                .build();
    }

    @PutMapping("/changeStatus/{colorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ColorResponse> changeStatus(@PathVariable long colorId) {
        return ApiResponse.<ColorResponse>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(colorService.changeStatus(colorId))
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<Page<ColorResponse>> getCategoriesByNameContains(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        return ApiResponse.<Page<ColorResponse>>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(colorService.getColorsByNameContains(name, page, size))
                .build();
    }
}
