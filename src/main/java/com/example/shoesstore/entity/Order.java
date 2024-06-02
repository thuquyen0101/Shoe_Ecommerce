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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long id;
    @Column(name = "status")
    private Integer status;
    @Column(name = "order_date")
    private Date orderDate;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "id_user")
    private User users;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

//    @ManyToMany(cascade = {
//            CascadeType.DETACH, CascadeType.MERGE,
//            CascadeType.PERSIST, CascadeType.REFRESH
//    })
//    @JoinTable (
//            name = "order_payment",
//            joinColumns = @JoinColumn(name = "id_order"),
//            inverseJoinColumns = @JoinColumn(name = "id_payment")
//    )
//    private List<Order> orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_payment")
    private Payment payment;
}
