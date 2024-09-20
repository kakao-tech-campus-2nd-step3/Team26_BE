package org.ktc2.cokaen.wouldyouin.controller.event.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
//globalwouldyouin.global.ApiResponseBody로 사용 필요
@Deprecated
public class ApiResponseBody<D> {

    private final Boolean status;
    private final D data;
}