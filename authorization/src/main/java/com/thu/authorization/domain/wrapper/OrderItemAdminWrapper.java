package com.thu.authorization.domain.wrapper;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor //constructor for fetching product for user view in OrderDao
@Builder
public class OrderItemAdminWrapper {
    private Integer product_id;
    private String name;
    private String description;
    private double wholesale_price;
    private double purchased_price;
    private int purchased_quantity;

}
