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
@Table(name = "size")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_size")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "size_length")
    private Long sizeLength;
    @Column(name = "status")
    private Integer status;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "created_by")
    private String createdBy;

    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL )
    private List<ShoeDetail> shoeDetails;
}
