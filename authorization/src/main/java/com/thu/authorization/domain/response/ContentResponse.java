package com.thu.authorization.domain.response;

import com.beaconfire.springsecuritycontent.domain.ServiceStatus;
import com.beaconfire.springsecuritycontent.entity.Content;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ContentResponse {
    ServiceStatus serviceStatus;
    Content content;
}
