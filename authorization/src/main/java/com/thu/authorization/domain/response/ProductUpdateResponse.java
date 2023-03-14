package com.thu.authorization.domain.response;

import com.thu.authorization.domain.ServiceStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductUpdateResponse {
    ServiceStatus serviceStatus;
    String message;

}
