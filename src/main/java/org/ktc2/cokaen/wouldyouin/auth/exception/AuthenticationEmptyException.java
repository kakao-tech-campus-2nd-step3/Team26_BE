package org.ktc2.cokaen.wouldyouin.auth.exception;

import org.ktc2.cokaen.wouldyouin._common.exception.BusinessException;
import org.ktc2.cokaen.wouldyouin._common.exception.ErrorCode;

public class AuthenticationEmptyException extends BusinessException {

    public AuthenticationEmptyException(String message) {
        super(message, ErrorCode.AUTHENTICATION_EMPTY);
    }

}
