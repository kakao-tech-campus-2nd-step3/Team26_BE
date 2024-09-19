package org.ktc2.cokaen.wouldyouin.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponseBody<D> {

    private final Boolean status;
    private final D data;
}