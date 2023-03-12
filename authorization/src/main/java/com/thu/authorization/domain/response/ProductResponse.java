package com.thu.authorization.domain.response;

import com.thu.authorization.domain.ServiceStatus;
import com.thu.authorization.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponse {
    ServiceStatus serviceStatus;
    Object product;
}
