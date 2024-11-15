package org.ktc2.cokaen.wouldyouin.auth.exception;

import org.ktc2.cokaen.wouldyouin._common.exception.BusinessException;
import org.ktc2.cokaen.wouldyouin._common.exception.ErrorCode;

public class JwtTokenException extends BusinessException {

    public JwtTokenException(String message) {
        super(message, ErrorCode.JWT_TOKEN_EXCEPTION);
    }
}
