package com.example.shoesstore.controller;

import com.example.shoesstore.constant.CodeStatusConstants;
import com.example.shoesstore.dto.request.ShoeDetailCreateRequest;
import com.example.shoesstore.dto.request.ShoeDetailSearchRequest;
import com.example.shoesstore.dto.request.ShoeDetailUpdateRequest;
import com.example.shoesstore.dto.response.ApiResponse;
import com.example.shoesstore.dto.response.ShoeDetailResponse;
import com.example.shoesstore.service.ShoeDetailService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/shoeDetail")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShoeDetailController {
    ShoeDetailService shoeDetailService;

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ShoeDetailResponse> createShoeDetail(@ModelAttribute @Valid ShoeDetailCreateRequest request) throws IOException {
        return ApiResponse.<ShoeDetailResponse>builder()
                .code(CodeStatusConstants.CREATED)
                .message("Success")
                .result(shoeDetailService.createShoeDetail(request))
                .build();
    }

    @GetMapping("/getShoeDetail/{idShoeDetail}")
    public ApiResponse<ShoeDetailResponse> getShoeDetailById(@PathVariable Long idShoeDetail) {
        return ApiResponse.<ShoeDetailResponse>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(shoeDetailService.getShoeDetailById(idShoeDetail))
                .build();
    }

    @GetMapping("/getAllShoeDetail")
    public ApiResponse<Page<ShoeDetailResponse>> getAllShoeDetail(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size

    ) {
        return ApiResponse.<Page<ShoeDetailResponse>>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(shoeDetailService.getAllShoeDetail(page, size))
                .build();
    }

    @PutMapping(value = "/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ShoeDetailResponse> updateShoeDetail(@ModelAttribute @Valid ShoeDetailUpdateRequest request, @PathVariable Long id) throws IOException {
        return ApiResponse.<ShoeDetailResponse>builder()
                .code(CodeStatusConstants.UPDATE)
                .message("Success")
                .result(shoeDetailService.updateShoeDetail(id, request))
                .build();
    }

    @GetMapping("/filter")
    public ApiResponse<Page<ShoeDetailResponse>> filter(
//            @RequestParam(required = false, defaultValue = "0") Integer page,
//            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false) Long idShoe,
            @RequestParam(required = false) Long idCategory,
            @RequestParam(required = false) Long idColor,
            @RequestParam(required = false) Long idSize,
            @RequestParam(required = false) Long price,
            Pageable pageable
            ) {
        ShoeDetailSearchRequest request = new ShoeDetailSearchRequest(idShoe, idCategory, idColor, idSize, price);
        log.info("idShoe {}", idShoe);
        log.info("idCategory {}", idCategory);
        log.info("idColor {}", idColor);
        log.info("idSize {}", idSize);
        log.info("price {}", price);

        return ApiResponse.<Page<ShoeDetailResponse>>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(shoeDetailService.filter(request, pageable))
                .build();
    }

    @PostMapping("/changeStatus/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ShoeDetailResponse> changeStatus(@PathVariable Long id) {
        return ApiResponse.<ShoeDetailResponse>builder()
                .code(CodeStatusConstants.UPDATE)
                .message("Success")
                .result(shoeDetailService.changeStatus(id))
                .build();
    }

}
