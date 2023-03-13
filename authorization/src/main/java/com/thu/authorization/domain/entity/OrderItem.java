package com.thu.authorization.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="OrderItem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", unique = true, nullable = false)
    private Integer order_item_id;

    @Column(name = "wholesale_price")
    private double wholesale_price;

    @Column(name = "purchased_price")
    private double purchased_price;

    @Column(name = "purchased_quantity")
    private int purchased_quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @ToString.Exclude
    private Order order;

}
