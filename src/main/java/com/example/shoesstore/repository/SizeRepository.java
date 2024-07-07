package com.example.shoesstore.repository;

import com.example.shoesstore.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    boolean existsSizeBySizeLength(int length);

    Page<Size> findSizesByStatus(int status, Pageable pageable);

}
