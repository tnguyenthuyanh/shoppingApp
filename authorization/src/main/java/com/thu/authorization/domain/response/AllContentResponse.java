package com.thu.authorization.domain.response;

import com.beaconfire.springsecuritycontent.domain.ServiceStatus;
import com.beaconfire.springsecuritycontent.entity.Content;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AllContentResponse {
    ServiceStatus serviceStatus;
    List<Content> contents;
}
