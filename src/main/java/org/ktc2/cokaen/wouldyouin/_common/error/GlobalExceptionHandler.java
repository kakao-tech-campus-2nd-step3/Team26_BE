package org.ktc2.cokaen.wouldyouin._common.error;


import java.util.stream.Collectors;
import org.ktc2.cokaen.wouldyouin.image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin._common.exception.BusinessException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseBody<Void>> handleBusinessException(BusinessException e) {
        return ApiResponse.error(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponseBody<Void>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        if (ex.getRequiredType() == ImageDomain.class) {
            return ApiResponse.error(ErrorCode.INVALID_IMAGE_DOMAIN, "해당 이미지 도메인에 대한 서비스가 존재하지 않습니다.");
        }
        return ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE, "잘못된 요청 값입니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseBody<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
//            .map((reslover) -> "필드명: " + reslover.getField() + reslover.getDefaultMessage())
            .collect(Collectors.joining(", ")));
    }
}