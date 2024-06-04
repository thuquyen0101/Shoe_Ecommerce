package com.example.shoesstore.repository;

import com.example.shoesstore.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByCategoryName(String name);

    Page<Category> findByCategoryNameContainingIgnoreCase(String name, Pageable pageable);

}
