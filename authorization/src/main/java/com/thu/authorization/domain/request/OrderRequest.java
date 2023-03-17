package com.thu.authorization.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {

    List<OrderRequest> order;
    int product_id;
    int quantity;
}
