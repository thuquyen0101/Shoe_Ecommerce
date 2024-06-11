package com.example.shoesstore.repository;

import com.example.shoesstore.entity.Shoe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ShoeRepository extends JpaRepository<Shoe, Long> {
    boolean existsByName(String name);

    @Query("SELECT s FROM Shoe s WHERE s.name like:name AND s.status=:status ")
    Page<Shoe> findShoesByStatusAndNameContainsIgnoreCase(@Param("name") String name, @Param("status") Integer status, Pageable pageable);

    Page<Shoe> findShoesByNameContainsIgnoreCase(@Param("name") String name, Pageable pageable);


}
