package org.ktc2.cokaen.wouldyouin.member.exception;

import org.ktc2.cokaen.wouldyouin._common.exception.BusinessException;
import org.ktc2.cokaen.wouldyouin._common.exception.ErrorCode;

public class EmailAlreadyExistsException extends BusinessException {

    public EmailAlreadyExistsException(String message) {
        super(message, ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
