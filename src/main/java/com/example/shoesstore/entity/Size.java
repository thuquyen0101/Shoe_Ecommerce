package com.example.shoesstore.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @Column(name = "code", unique = true, nullable = false)
    private String code;
    @Column(name = "size_length")
    private Integer sizeLength;
    @Column(name = "status")
    private Integer status;
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "created_by")
    private String createdBy;

    @PrePersist
    public void generateCode() {
        if (this.code == null || this.code.isEmpty()) {
            this.code = UUID.randomUUID().toString();
        }
    }

    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL )
    @JsonManagedReference
    private List<ShoeDetail> shoeDetails;
}
