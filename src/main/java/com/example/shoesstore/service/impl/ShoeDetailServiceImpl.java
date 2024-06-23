package com.example.shoesstore.service.impl;

import com.example.shoesstore.constant.ShoeDetailStatus;
import com.example.shoesstore.dto.request.ShoeDetailCreateRequest;
import com.example.shoesstore.dto.request.ShoeDetailSearchRequest;
import com.example.shoesstore.dto.request.ShoeDetailUpdateRequest;
import com.example.shoesstore.dto.response.ShoeDetailResponse;
import com.example.shoesstore.entity.Category;
import com.example.shoesstore.entity.Color;
import com.example.shoesstore.entity.Image;
import com.example.shoesstore.entity.Shoe;
import com.example.shoesstore.entity.ShoeDetail;
import com.example.shoesstore.entity.Size;
import com.example.shoesstore.entity.User;
import com.example.shoesstore.exception.AppException;
import com.example.shoesstore.exception.ErrorCode;
import com.example.shoesstore.mapper.ShoeDetailMapper;
import com.example.shoesstore.repository.CategoryRepository;
import com.example.shoesstore.repository.ColorRepository;
import com.example.shoesstore.repository.ImageRepository;
import com.example.shoesstore.repository.ShoeDetailRepository;
import com.example.shoesstore.repository.ShoeRepository;
import com.example.shoesstore.repository.SizeRepository;
import com.example.shoesstore.repository.UserRepository;
import com.example.shoesstore.service.ImageService;
import com.example.shoesstore.service.ShoeDetailService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ShoeDetailServiceImpl implements ShoeDetailService {
    ShoeDetailRepository shoeDetailRepository;
    UserRepository userRepository;
    ColorRepository colorRepository;
    SizeRepository sizeRepository;
    CategoryRepository categoryRepository;
    ShoeRepository shoeRepository;
    ImageRepository imageRepository;
    ImageService imageService;
    ShoeDetailMapper shoeDetailMapper;
    EntityManager entityManager;

    @Override
    public ShoeDetailResponse createShoeDetail(ShoeDetailCreateRequest request) throws IOException {
        Optional<User> user = Optional.ofNullable(
                userRepository.findById(request.getCreatedBy())
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));

        if (user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        ShoeDetail detail = shoeDetailMapper.mapToShoe(request);
        Optional<Color> color = colorRepository.findById(request.getIdColor());
        Optional<Size> size = sizeRepository.findById(request.getIdSize());
        Optional<Category> category = categoryRepository.findById(request.getIdCategory());
        Optional<Shoe> shoe = shoeRepository.findById(request.getIdShoe());

        detail.setColor(color.get());
        detail.setSize(size.get());
        detail.setCategory(category.get());
        detail.setShoe(shoe.get());
        detail.setStatus(ShoeDetailStatus.ACTIVE);
        detail.setCreatedBy(user.get().getName());

        String url = imageService.uploadImage(request.getFile());
        log.info("url " + url);
        Image image = new Image();
        image.setImageUrl(url);
        imageRepository.save(image);
        detail.setImage(image);

        return shoeDetailMapper.mapToResponse(shoeDetailRepository.save(detail));
    }

    @Override
    public ShoeDetailResponse getShoeDetailById(long idShoeDetail) {
        return shoeDetailMapper.mapToResponse(shoeDetailRepository.findById(idShoeDetail)
                .orElseThrow(() -> new AppException(ErrorCode.SHOEDETAIL_NOT_FOUND)));
    }

    @Override
    public Page<ShoeDetailResponse> getAllShoeDetail(int page, int size) {
        return shoeDetailRepository.findAll(PageRequest.of(page, size))
                .map(detail -> shoeDetailMapper.mapToResponse(detail));
    }

    @Override
    public ShoeDetailResponse updateShoeDetail(long id, ShoeDetailUpdateRequest request) throws IOException {
        ShoeDetail shoeDetail = shoeDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SHOEDETAIL_NOT_FOUND));
        Optional<Color> color = colorRepository.findById(request.getIdColor());
        Optional<Size> size = sizeRepository.findById(request.getIdSize());
        Optional<Category> category = categoryRepository.findById(request.getIdCategory());
        Optional<Shoe> shoe = shoeRepository.findById(request.getIdShoe());

        shoeDetail.setColor(color.get());
        shoeDetail.setSize(size.get());
        shoeDetail.setCategory(category.get());
        shoeDetail.setShoe(shoe.get());

        String url = imageService.uploadImage(request.getFile());
        Image image = new Image();
        image.setImageUrl(url);
        imageRepository.save(image);
        shoeDetail.setImage(image);

        shoeDetailMapper.updateShoeDetail(shoeDetail, request);
        return shoeDetailMapper.mapToResponse(shoeDetailRepository.save(shoeDetail));
    }

    @Override
    public Page<ShoeDetailResponse> filter(ShoeDetailSearchRequest request, Pageable pageable) {
        List<ShoeDetail> shoeDetails =  shoeDetailRepository.findAll();

        if (request.getIdShoe() != null) {
            shoeDetails.removeIf(shoeDetail -> !shoeDetail.getShoe().getId().equals(request.getIdShoe()));
        }
        if (request.getIdCategory() != null) {
            shoeDetails.removeIf(shoeDetail -> !shoeDetail.getCategory().getId().equals(request.getIdCategory()));
        }
        if (request.getIdColor() != null) {
            shoeDetails.removeIf(shoeDetail -> !shoeDetail.getColor().getId().equals(request.getIdColor()));
        }
        if (request.getIdSize() != null) {
            shoeDetails.removeIf(shoeDetail -> !shoeDetail.getSize().getId().equals(request.getIdSize()));
        }
        if (request.getPrice() != null) {
            shoeDetails.removeIf(shoeDetail -> shoeDetail.getPrice() < 0 || shoeDetail.getPrice() > request.getPrice());
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), shoeDetails.size());
        Page<ShoeDetail> page = new PageImpl<>(shoeDetails.subList(start, end), pageable, shoeDetails.size());

        return page.map(detail -> shoeDetailMapper.mapToResponse(detail));
    }


}
