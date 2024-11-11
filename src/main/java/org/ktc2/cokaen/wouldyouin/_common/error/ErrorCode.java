package org.ktc2.cokaen.wouldyouin._common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // TODO: status,code, message 수정

    UNEXPECTED(HttpStatus.BAD_REQUEST.value(), "-1", "예상치 못한 오류가 발생했습니다."),

    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "-10404", "엔티티를 찾을 수 없습니다."),

    NO_LEFT_SEAT(HttpStatus.BAD_REQUEST.value(), "-10400", "남은 좌석이 부족합니다."),

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), "-20400", "Invalid Input Value"),

    FAIL_TO_READ_IMAGE(HttpStatus.CONFLICT.value(), "-20400", "Fail to read image"),

    FAIL_TO_PAY(HttpStatus.CONFLICT.value(), "-20400", "Fail to %s"),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "-20400", "Unauthorized, %s."),

    FAIL_TO_UPLOAD_IMAGE(HttpStatus.CONFLICT.value(), "-20400", "Fail to upload image"),

    FAIL_TO_DELETE_IMAGE(HttpStatus.CONFLICT.value(), "-20400", "Fail to delete image"),

    URL_PARSING_FAILED(HttpStatus.BAD_REQUEST.value(), "-20400", "URL 파싱에 실패했습니다."),

    CURRENT_LOCATION_EMPTY(HttpStatus.BAD_REQUEST.value(), "-20400", "현재 위치 정보를 찾을 수 없습니다.");

    private final Integer status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
