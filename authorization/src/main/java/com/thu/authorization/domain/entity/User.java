package com.thu.authorization.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="User")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer user_id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")// default fetch type is LAZY
    @ToString.Exclude // to avoid infinite loop
    private Set<ProductWatchlist> ProductWatchlist = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")// default fetch type is LAZY
    @ToString.Exclude // to avoid infinite loop
    private Set<Order> orders = new HashSet<>();


}
