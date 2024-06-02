package com.example.shoesstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "status")
    private Integer status;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL )
    private List<ShoeDetail> shoeDetails;
}
