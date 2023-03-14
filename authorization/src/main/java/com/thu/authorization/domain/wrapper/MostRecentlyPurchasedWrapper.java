package com.thu.authorization.domain.wrapper;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor //constructor for fetching product for user view in OrderDao
@NoArgsConstructor
@Builder
public class MostRecentlyPurchasedWrapper {
    private Integer product_id;
    private String date_placed;
    private String name;
    private String description;
    private int purchased_quantity;
    private double purchased_price;
}
