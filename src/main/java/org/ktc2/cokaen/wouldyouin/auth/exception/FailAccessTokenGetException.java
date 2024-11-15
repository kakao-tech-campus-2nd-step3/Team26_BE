package org.ktc2.cokaen.wouldyouin.auth.exception;

import org.ktc2.cokaen.wouldyouin._common.exception.BusinessException;
import org.ktc2.cokaen.wouldyouin._common.exception.ErrorCode;

public class FailAccessTokenGetException extends BusinessException {

    public FailAccessTokenGetException(String message) {
        super(message, ErrorCode.FAIL_ACCESS_TOKEN_GET);
    }

}
