package org.ktc2.cokaen.wouldyouin._common.error;


import java.util.stream.Collectors;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin._common.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseBody<Void>> handleBusinessException(BusinessException e) {
        return ApiResponse.error(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseBody<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult().getFieldErrors().stream()
            .map(error -> String.format("%s %s", error.getField(), error.getDefaultMessage()))
            .collect(Collectors.joining(",")));
    }
}