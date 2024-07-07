package com.example.shoesstore.controller;

import com.example.shoesstore.constant.CodeStatusConstants;
import com.example.shoesstore.dto.response.ApiResponse;
import com.example.shoesstore.service.ImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/image")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ImageController {
     ImageService imageService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return ApiResponse.<String>builder()
                .code(CodeStatusConstants.CREATED)
                .message("Upload success")
                .result(imageService.uploadImage(file))
                .build();

    }

    @GetMapping(value = "/get/{code}")
    public ResponseEntity<byte[]> getImage(@PathVariable String code) {
        try {
            byte[] image = imageService.getImage(code);

            String extension = FilenameUtils.getExtension(code).toLowerCase();
            MediaType mediaType = MediaType.IMAGE_JPEG;

            if (extension.equals("png")) {
                mediaType = MediaType.IMAGE_PNG;
            } else if (extension.equals("gif")) {
                mediaType = MediaType.IMAGE_GIF;
            }

            return ResponseEntity.ok().contentType(mediaType).body(image);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
