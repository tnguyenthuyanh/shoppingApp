package com.thu.authorization.domain.wrapper;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor //constructor for fetching product for user view in OrderDao
@NoArgsConstructor
@Builder
public class AllOrderResultAdminWrapper {

    List<OrderResultAdminWrapper> orders;

}
