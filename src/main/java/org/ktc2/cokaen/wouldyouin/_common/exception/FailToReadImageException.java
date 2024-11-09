package org.ktc2.cokaen.wouldyouin._common.exception;

import org.ktc2.cokaen.wouldyouin._common.error.ErrorCode;

public class FailToReadImageException extends BusinessException {

    public FailToReadImageException(String message) {
        super(message, ErrorCode.FAIL_TO_READ_IMAGE);
    }
}