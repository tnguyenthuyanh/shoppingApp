package com.thu.authorization.domain.wrapper;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor //constructor for fetching product for user view in OrderDao
@NoArgsConstructor
@Builder
public class MostProfitProductWrapper {
    private Integer product_id;
    private String name;
    private String description;
    private double profit;
}
