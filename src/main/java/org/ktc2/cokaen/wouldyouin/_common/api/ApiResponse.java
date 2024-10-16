package org.ktc2.cokaen.wouldyouin._common.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {

    public static <D> ResponseEntity<ApiResponseBody<D>> ok(D data) {
        return ResponseEntity.ok(new ApiResponseBody<>(true, data));
    }

    public static <D> ResponseEntity<ApiResponseBody<D>> created(D data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseBody<>(true, data));
    }

    public static <D> ResponseEntity<ApiResponseBody<D>> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponseBody<>(true, null));
    }
}