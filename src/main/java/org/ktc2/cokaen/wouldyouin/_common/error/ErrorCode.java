package org.ktc2.cokaen.wouldyouin._common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    UNEXPECTED(HttpStatus.BAD_REQUEST.value(), "-1", "Unexpected exception occurred"),

    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "-10404", "Can not find a %s Entity"),

    NO_LEFT_SEAT(HttpStatus.BAD_REQUEST.value(), "-10400", "No left seat"),

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), "-20400", "Invalid Input Value"),

    FAIL_TO_READ_IMAGE(HttpStatus.CONFLICT.value(), "-20400", "Fail to read image"),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "-20400", "Unauthorized, %s id is not matched."),

    ENTITY_PARAM_IS_NULL(HttpStatus.BAD_REQUEST.value(), "-20400", "%s is null");

    private final Integer status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
