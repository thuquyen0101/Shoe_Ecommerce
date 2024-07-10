package com.example.shoesstore.service.impl;

import com.example.shoesstore.entity.Image;
import com.example.shoesstore.exception.AppException;
import com.example.shoesstore.exception.ErrorCode;
import com.example.shoesstore.repository.ImageRepository;
import com.example.shoesstore.service.ImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ImageServiceImpl implements ImageService {
    ImageRepository imageRepository;
    String serveImageURL;
    String serverURL;
    private static final Path IMG_PATH = Paths.get("src/images");

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        createDirectoryIfNotExists();

        String code = UUID.randomUUID().toString();

        try {
            Path fileSave = IMG_PATH.resolve(code + "-" + Objects.requireNonNull(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), fileSave, StandardCopyOption.REPLACE_EXISTING);
            String fileName = fileSave.getFileName().toString();
            log.info("fileSave {} "+fileSave);
            Image image1 = new Image();
            image1.setImageUrl(code);
            image1.setStatus(1);
//            imageRepository.save(image1);
            log.info("IMAGE PATH {}" , IMG_PATH);
            log.info("serverURL " + serverURL);
            log.info("serveImageURL " + serveImageURL);
            log.info("code " + code);
            log.info("fileSave " + fileSave);

// return serverURL + "/" + serveImageURL + "/" + code
            return code;
        } catch (IOException e) {
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        }
    }

    @Override
    public byte[] getImage(String imageName) throws IOException {
        try (Stream<Path> walk = Files.walk(IMG_PATH)) {
            Optional<Path> matchingFile = walk.filter(Files::isRegularFile).filter(path -> path.getFileName().toString().startsWith(imageName)).findFirst();

            if (matchingFile.isPresent()) {
                return Files.readAllBytes(matchingFile.get());
            } else {
                throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
            }
        }
    }

    private void createDirectoryIfNotExists() throws IOException {
        if (!Files.exists(IMG_PATH)) {
            Files.createDirectories(IMG_PATH);
        }
    }
}





