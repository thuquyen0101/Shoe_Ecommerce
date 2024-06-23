package com.example.shoesstore.repository;

import com.example.shoesstore.dto.request.ShoeDetailSearchRequest;
import com.example.shoesstore.dto.response.ShoeDetailResponse;
import com.example.shoesstore.entity.ShoeDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoeDetailRepository extends JpaRepository<ShoeDetail, Long> {

}
