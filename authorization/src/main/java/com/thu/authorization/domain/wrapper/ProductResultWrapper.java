package com.thu.authorization.domain.wrapper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ProductResultWrapper {

    // constructor for fetching product for user view in ProductDao
    public ProductResultWrapper(Integer product_id, String name, String description, double retail_price) {
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.retail_price = retail_price;
    }
    private Integer product_id;

    private String name;

    private String description;

    private double retail_price;

}
