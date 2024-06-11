package com.example.shoesstore.controller;

import com.example.shoesstore.constant.CodeStatusConstants;
import com.example.shoesstore.dto.request.ShoeCreateRequest;
import com.example.shoesstore.dto.request.ShoeUpdateRequest;
import com.example.shoesstore.dto.response.ApiResponse;
import com.example.shoesstore.dto.response.ShoeResponse;
import com.example.shoesstore.service.ShoeService;
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
@RequestMapping("/api/v1/shoe")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ShoeController {
    ShoeService service;
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ShoeResponse> create(@RequestBody @Valid ShoeCreateRequest request){
        return ApiResponse.<ShoeResponse>builder()
                .code(CodeStatusConstants.CREATED)
                .message("Success")
                .result(service.createShoe(request))
                .build();
    }

    @GetMapping("/getShoe/{id}")
    public ApiResponse<ShoeResponse> getShoeById(@PathVariable long id){
        return ApiResponse.<ShoeResponse>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(service.getShoeById(id))
                .build();
    }

    @GetMapping("/getAll")
    public ApiResponse<Page<ShoeResponse>> getAllShoe(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5")  Integer size
    ){
        return ApiResponse.<Page<ShoeResponse>>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(service.getAllShoe(page, size))
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ShoeResponse> updateShoe(@PathVariable long id, @RequestBody @Valid ShoeUpdateRequest request){
        return ApiResponse.<ShoeResponse>builder()
                .code(CodeStatusConstants.UPDATE)
                .message("Success")
                .result(service.updateShoe(id, request))
                .build();
    }

    @PutMapping("/changeStatus/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ShoeResponse> changeStatus(@PathVariable long id){
        return ApiResponse.<ShoeResponse>builder()
                .code(CodeStatusConstants.UPDATE)
                .message("Changed status")
                .result(service.changeStatus(id))
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<Page<ShoeResponse>> search(
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "status") String status,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5")  Integer size
    ){
        log.info("status {}", status);
        log.info("name {}", name);
        return ApiResponse.<Page<ShoeResponse>>builder()
                .code(CodeStatusConstants.OK)
                .message("Success")
                .result(service.searchByNameOrStatus(name, status, page, size))
                .build();
    }
}
