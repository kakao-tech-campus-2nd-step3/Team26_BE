package org.ktc2.cokaen.wouldyouin._common.exception;

public class FailToReadImageException extends BusinessException {

    public FailToReadImageException(String message) {
        super(message, ErrorCode.FAIL_TO_READ_IMAGE);
    }
}