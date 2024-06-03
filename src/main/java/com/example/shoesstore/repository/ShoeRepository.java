package com.example.shoesstore.repository;

import com.example.shoesstore.entity.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShoeRepository extends JpaRepository<Shoe, Long> {

}
