package com.thu.authorization.domain.wrapper;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor //constructor for fetching product for user view in OrderDao
@NoArgsConstructor
@Builder
public class OrderResultAdminWrapper {

    private int order_id;
    private int user_id;
    private String order_status;
    private String date_placed;

    List<OrderItemAdminWrapper> orderItems;

}
