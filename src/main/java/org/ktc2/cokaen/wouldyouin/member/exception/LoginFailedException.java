package org.ktc2.cokaen.wouldyouin.member.exception;

import org.ktc2.cokaen.wouldyouin._common.exception.BusinessException;
import org.ktc2.cokaen.wouldyouin._common.exception.ErrorCode;

public class LoginFailedException extends BusinessException {
    public LoginFailedException(String message) {
        super(message, ErrorCode.LOGIN_FAILED);
    }

}
