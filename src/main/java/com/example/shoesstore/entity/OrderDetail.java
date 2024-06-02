package com.example.shoesstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_detail")
    private Long id;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private Long price;
    @Column(name = "total_money")
    private Long totalMoney;
    @Column(name = "money_give")
    private Long moneyGive;
    @Column(name = "money_back")
    private Long moneyBack;
    @Column(name = "status")
    private Integer status;
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private String createdBy;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = {
//            CascadeType.DETACH, CascadeType.MERGE,
//            CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinColumn(name = "id_order")
//    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_shoe_detail")
    private ShoeDetail shoeDetail;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_order")
    private Order orders;
}
