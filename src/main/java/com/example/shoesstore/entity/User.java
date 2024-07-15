package com.example.shoesstore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @Column(name = "avatar", columnDefinition = "TEXT")
    private String urlAvatar;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "status")
    private Integer status;
    @Column(name = "address")
    private String address;
    @Column(name = "fb_account")
    private String facebookAccount;
    @Column(name = "gg_account")
    private String googleAccount;
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "created_by")
    private String createdBy;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "id_user")}, inverseJoinColumns = {@JoinColumn(name = "id_role")})
    private List<Role> roles;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToOne(mappedBy = "users")
    @JsonBackReference
    private Cart cart;
}
