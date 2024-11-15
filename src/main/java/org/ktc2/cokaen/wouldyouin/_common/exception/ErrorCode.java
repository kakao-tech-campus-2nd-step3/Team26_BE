package org.ktc2.cokaen.wouldyouin._common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "-40904"),
    FAIL_TO_DELETE_IMAGE(HttpStatus.CONFLICT.value(), "-40903"),
    FAIL_TO_UPLOAD_IMAGE(HttpStatus.CONFLICT.value(), "-40902"),
    FAIL_TO_PAY(HttpStatus.CONFLICT.value(), "-40901"),
    FAIL_TO_READ_IMAGE(HttpStatus.CONFLICT.value(), "-40900"),

    RESERVATION_NOT_FOUND_FOR_REVIEW(HttpStatus.NOT_FOUND.value(), "40401"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "-40400"),

    SECURITY_FILTER_AUTHORIZATION_REQUIRED(HttpStatus.FORBIDDEN.value(), "-40300"),

    FAIL_SOCIAL_DATA_GET(HttpStatus.UNAUTHORIZED.value(), "-40106"),
    FAIL_ACCESS_TOKEN_GET(HttpStatus.UNAUTHORIZED.value(), "-40105"),
    AUTHENTICATION_EMPTY(HttpStatus.UNAUTHORIZED.value(), "-40104"),
    JWT_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED.value(), "-40103"),
    ADDITIONAL_INFO_ILLEGAL_ACCESS(HttpStatus.UNAUTHORIZED.value(), "-40102"),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED.value(), "-40101"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "-40100"),

    INVALID_AUTHORIZATION_HEADER(HttpStatus.BAD_REQUEST.value(), "-40008"),
    SECURITY_FILTER_FORBIDDEN_ACCESS(HttpStatus.BAD_REQUEST.value(), "-40007"),
    NOT_LIKEABLE_MEMBER_TYPE(HttpStatus.BAD_REQUEST.value(), "-40006"),
    CURRENT_LOCATION_EMPTY(HttpStatus.BAD_REQUEST.value(), "-40005"),
    INVALID_IMAGE_DOMAIN(HttpStatus.BAD_REQUEST.value(), "-40004"),
    NO_LEFT_SEAT(HttpStatus.BAD_REQUEST.value(), "-40003"),
    URL_PARSING_FAILED(HttpStatus.BAD_REQUEST.value(), "-40002"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), "-40001"),
    UNEXPECTED(HttpStatus.BAD_REQUEST.value(), "-40000");

    private final Integer status;
    private final String code;

    ErrorCode(int status, String code) {
        this.status = status;
        this.code = code;
    }
}
