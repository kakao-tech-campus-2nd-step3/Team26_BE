package org.ktc2.cokaen.wouldyouin._common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
// TODO: code 수정 필요
public enum ErrorCode {

    FAIL_SOCIAL_DATA_GET(HttpStatus.UNAUTHORIZED.value(), "-20400"),

    FAIL_ACCESS_TOKEN_GET(HttpStatus.UNAUTHORIZED.value(), "-20400"),

    AUTHENTICATION_EMPTY(HttpStatus.UNAUTHORIZED.value(), "-20400"),

    JWT_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED.value(), "-20400"),

    INVALID_AUTHORIZATION_HEADER(HttpStatus.BAD_REQUEST.value(), "-20400"),

    SECURITY_FILTER_AUTHORIZATION_REQUIRED(HttpStatus.FORBIDDEN.value(), "-20400"),

    SECURITY_FILTER_FORBIDDEN_ACCESS(HttpStatus.BAD_REQUEST.value(), "-20400"),

    NOT_LIKEABLE_MEMBER_TYPE(HttpStatus.BAD_REQUEST.value(), "-20400"),

    ADDITIONAL_INFO_ILLEGAL_ACCESS(HttpStatus.UNAUTHORIZED.value(), "-20400"),

    LOGIN_FAILED(HttpStatus.UNAUTHORIZED.value(), "-20400"),

    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "-10400"),

    CURRENT_LOCATION_EMPTY(HttpStatus.BAD_REQUEST.value(), "-20400"),

    INVALID_IMAGE_DOMAIN(HttpStatus.BAD_REQUEST.value(), "-20400"),

    FAIL_TO_DELETE_IMAGE(HttpStatus.CONFLICT.value(), "-20400"),

    FAIL_TO_UPLOAD_IMAGE(HttpStatus.CONFLICT.value(), "-20400"),

    FAIL_TO_PAY(HttpStatus.CONFLICT.value(), "-20400"),

    FAIL_TO_READ_IMAGE(HttpStatus.CONFLICT.value(), "-20400"),

    RESERVATION_NOT_FOUND_FOR_REVIEW(HttpStatus.NOT_FOUND.value(), "14044"),

    NO_LEFT_SEAT(HttpStatus.BAD_REQUEST.value(), "-10400"),

    URL_PARSING_FAILED(HttpStatus.BAD_REQUEST.value(), "-20400"),

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), "-20400"),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "-20400"),

    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "-10404"),

    UNEXPECTED(HttpStatus.BAD_REQUEST.value(), "-1");

    private final Integer status;
    private final String code;

    ErrorCode(int status, String code) {
        this.status = status;
        this.code = code;
    }
}
