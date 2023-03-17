package com.thu.auth.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="User_table")
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
    private Set<Permission> permissions = new HashSet<>();
}
