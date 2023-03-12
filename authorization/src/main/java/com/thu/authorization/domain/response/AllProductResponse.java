package com.thu.authorization.domain.response;

import com.thu.authorization.domain.ServiceStatus;
import com.thu.authorization.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AllProductResponse {
    ServiceStatus serviceStatus;
    List<Product> products;
}
