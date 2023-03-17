package com.thu.authorization.domain.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@Builder
public class ProductRequest {

    @NotNull
    @Size(max = 20, min = 1, message = "product name should be from 1-20 characters")
    String name;

    @Size(max = 40, message = "description should be at most 40 characters")
    String description;

    @NotNull
    double wholesale_price;
    @NotNull
    double retail_price;
    @NotNull
    int stock_quantity;
}
