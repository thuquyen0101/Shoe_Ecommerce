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
    @Query(value = "select sd.id_shoe_detail, sd.quantity, sd.price, sd.status, sd.created_at, sd.created_by, " +
            "sd.id_shoe, sd.id_category, sd.id_color, sd.id_size, sd.id_image " +
            "from shoe_detail sd " +
            "join shoe s ON sd.id_shoe = s.id_shoe " +
            "join category c on sd.id_category = c.id_category " +
            "join color c2 on sd.id_color = c2.id_color " +
            "join `size` s2 on sd.id_size = s2.id_size " +
            "join image i on sd.id_image = i.id_image " +
            "where (:idShoe IS NULL OR s.id_shoe = :idShoe) " +
            "and (:idCategory is null OR c.id_category =:idCategory ) " +
            "and (:idColor is null or  c2.id_color =:idColor  ) " +
            "and (:idSize is null or s2.id_size =:idSize  ) " +
            "and (:price IS NULL or 0 <= sd.price and sd.price <= :price) ", nativeQuery = true)
    Page<ShoeDetail> filter(@Param("idShoe") Long idShoe,
                                    @Param("idCategory") Long idCategory,
                                    @Param("idColor") Long idColor,
                                    @Param("idSize") Long idSize,
                                    @Param("price") Long price,
                                    Pageable pageable);
}
