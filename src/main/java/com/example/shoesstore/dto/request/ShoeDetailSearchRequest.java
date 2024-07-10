package com.example.shoesstore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ShoeDetailSearchRequest {
    Long idShoe;
    Long idCategory;
    Long idColor;
    Long idSize;
    String name;
    Long minPrice;
    Long maxPrice;
    Date minDate;
    Date maxDate;
    Boolean arrangeDate;
    Boolean arrangePrice;
    Boolean arrangeName;

}
