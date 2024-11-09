package org.ktc2.cokaen.wouldyouin._common.exception;

import org.ktc2.cokaen.wouldyouin._common.error.ErrorCode;

public class FailedToPayException extends BusinessException {

    public FailedToPayException(String message) {
        super(message, ErrorCode.FAIL_TO_PAY);
    }
}