package com.example.shoesstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image")
    private Long id;
    @Column(name = "url")
    private String imageUrl;
    @Column(name = "status")
    private Integer status;
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

   @OneToMany(mappedBy = "image", cascade = CascadeType.ALL )
    private List<ShoeDetail> shoeDetails;
}
