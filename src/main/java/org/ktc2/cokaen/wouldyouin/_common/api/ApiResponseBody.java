package org.ktc2.cokaen.wouldyouin._common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponseBody<D> {

    private final Boolean status;
    private final D data;
}