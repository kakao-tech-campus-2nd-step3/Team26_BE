package org.ktc2.cokaen.wouldyouin._common.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseBody<D> {

    private final Boolean success;

    // 성공한 경우
    private final D data;

    // 실패한 경우
    private final String code;
    private final String message;

    public ApiResponseBody(Boolean success, D data) {
        this.success = success;
        this.data = data;
        this.code = null;
        this.message = null;
    }

    public ApiResponseBody(Boolean success, String code, String message) {
        this.success = success;
        this.data = null;
        this.code = code;
        this.message = message;
    }
}