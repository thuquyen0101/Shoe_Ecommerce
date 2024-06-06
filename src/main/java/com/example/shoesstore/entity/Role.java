package com.example.shoesstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long id;
    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "id_role")},
            inverseJoinColumns = {@JoinColumn(name = "id_user")}
    )
    private List<User> listUser;

}
