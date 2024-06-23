package com.example.shoesstore.repository;

import com.example.shoesstore.dto.response.ColorResponse;
import com.example.shoesstore.entity.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    boolean existsByName(String colorName);

    Page<Color> findByNameContainsIgnoreCase(String name, Pageable pageable);

}
