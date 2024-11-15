package org.ktc2.cokaen.wouldyouin.payment.exception;

import org.ktc2.cokaen.wouldyouin._common.exception.BusinessException;
import org.ktc2.cokaen.wouldyouin._common.exception.ErrorCode;

public class FailedToPayException extends BusinessException {

    public FailedToPayException(String message) {
        super(message, ErrorCode.FAIL_TO_PAY);
    }
}