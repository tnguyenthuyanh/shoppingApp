package com.thu.authorization.domain.response;

import com.thu.authorization.domain.ServiceStatus;
import com.thu.authorization.domain.entity.Order;
import com.thu.authorization.domain.wrapper.OrderItemResultWrapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderResponse {
    ServiceStatus serviceStatus;
    String message;
    Object order;
}

