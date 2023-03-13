package com.thu.authorization.domain.response;

import com.thu.authorization.domain.ServiceStatus;
import com.thu.authorization.domain.wrapper.MostSpentWrapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MostSpentResponse {
    ServiceStatus serviceStatus;
    String message;
    List<MostSpentWrapper> mostSpentResponse;
}

