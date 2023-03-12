package com.thu.authorization.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", unique = true, nullable = false)
    private Integer product_id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "wholesale_price")
    private double wholesale_price;

    @Column(name = "retail_price")
    private double retail_price;

    @Column(name = "stock_quantity")
    private int stock_quantity;


//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")// default fetch type is LAZY
//    @ToString.Exclude // to avoid infinite loop
//    private Set<OrderItem> orderItems = new HashSet<>();
}
