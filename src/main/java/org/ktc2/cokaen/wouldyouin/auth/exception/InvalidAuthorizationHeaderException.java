package org.ktc2.cokaen.wouldyouin.auth.exception;

import org.ktc2.cokaen.wouldyouin._common.exception.BusinessException;
import org.ktc2.cokaen.wouldyouin._common.exception.ErrorCode;

public class InvalidAuthorizationHeaderException extends BusinessException {

    public InvalidAuthorizationHeaderException(String message) {
        super(message, ErrorCode.INVALID_AUTHORIZATION_HEADER);
    }

}
