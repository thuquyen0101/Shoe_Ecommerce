package com.example.shoesstore.controller;

import com.example.shoesstore.constant.CodeStatusConstants;
import com.example.shoesstore.dto.request.SizeCreateRequest;
import com.example.shoesstore.dto.request.SizeUpdateRequest;
import com.example.shoesstore.dto.response.ApiResponse;
import com.example.shoesstore.dto.response.SizeResponse;
import com.example.shoesstore.service.SizeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/size")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SizeController {
    SizeService service;

    @GetMapping("/getAll")
    public ApiResponse<Page<SizeResponse>> getAllSize(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        return ApiResponse.<Page<SizeResponse>>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(service.getAllSize(page, size))
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<SizeResponse> createSize(@RequestBody @Valid SizeCreateRequest request) {
        return ApiResponse.<SizeResponse>builder()
                .code(CodeStatusConstants.CREATED)
                .message("Success")
                .result(service.createSize(request))
                .build();
    }

    @GetMapping("/getSize/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<SizeResponse> getOneSize(@PathVariable int id) {
        return ApiResponse.<SizeResponse>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(service.getSizeById(id))
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<SizeResponse> updateSize(@RequestBody @Valid SizeUpdateRequest request, @PathVariable long id) {
        return ApiResponse.<SizeResponse>builder()
                .code(CodeStatusConstants.UPDATE)
                .message("Success")
                .result(service.updateSize(id, request))
                .build();
    }

    @PutMapping("/updateStatus/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<SizeResponse> updateStatus(@PathVariable long id) {
        return ApiResponse.<SizeResponse>builder()
                .code(CodeStatusConstants.UPDATE)
                .message("Success")
                .result(service.updateStatus(id))
                .build();
    }

    @GetMapping("/filter/{status}")
    public ApiResponse<Page<SizeResponse>> filterStatus(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @PathVariable int status
    ) {
        return ApiResponse.<Page<SizeResponse>>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(service.filterSize(status, page, size))
                .build();
    }
}
