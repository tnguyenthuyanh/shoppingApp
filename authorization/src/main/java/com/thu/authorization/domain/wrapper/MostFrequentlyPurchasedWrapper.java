package com.thu.authorization.domain.wrapper;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor //constructor for fetching product for user view in OrderDao
@NoArgsConstructor
@Builder
public class MostFrequentlyPurchasedWrapper {
    private Integer product_id;
    private String name;
    private String description;
    private int purchased_quantity;
}
