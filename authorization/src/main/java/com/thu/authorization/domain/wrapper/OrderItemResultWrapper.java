package com.thu.authorization.domain.wrapper;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor //constructor for fetching product for user view in OrderDao
@Builder
public class OrderItemResultWrapper {

    private Integer product_id;
    private String name;
    private String description;
    private double purchased_price;
    private int purchased_quantity;

}
