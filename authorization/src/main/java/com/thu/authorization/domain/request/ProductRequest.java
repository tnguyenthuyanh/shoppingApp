package com.thu.authorization.domain.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductRequest {
    String name;
    String description;
    double wholesale_price;
    double retail_price;
    int stock_quantity;
}
