package org.ktc2.cokaen.wouldyouin.controller.event.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponseBody<D> {

    private final Boolean status;
    private final D data;
}