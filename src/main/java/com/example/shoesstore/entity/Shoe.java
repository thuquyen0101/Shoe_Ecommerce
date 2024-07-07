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
@Table(name = "shoe")
public class Shoe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_shoe")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "status")
    private Integer status;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Long price;
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "created_by")
    private String createBy;

    @PrePersist
    public void generateCode(){
        if(this.code == null || this.code.isEmpty()){
            this.code = UUID.randomUUID().toString();
        }
    }

    @OneToMany(mappedBy = "shoe", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ShoeDetail> shoeDetails;

}
