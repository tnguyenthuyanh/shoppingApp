package com.thu.authorization.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Order_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", unique = true, nullable = false)
    private Integer order_id;

    @Column(name = "order_status")
    private String order_status;

    @Column(name = "date_placed", insertable=false) // to get default value when inserting
    private String date_placed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")// default fetch type is LAZY
    @ToString.Exclude // to avoid infinite loop
    private Set<OrderItem> orderItems = new HashSet<>();
}
