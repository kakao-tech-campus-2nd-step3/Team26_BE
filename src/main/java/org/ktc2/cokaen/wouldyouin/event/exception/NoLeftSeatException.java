package org.ktc2.cokaen.wouldyouin.event.exception;

import org.ktc2.cokaen.wouldyouin._common.exception.BusinessException;
import org.ktc2.cokaen.wouldyouin._common.exception.ErrorCode;

public class NoLeftSeatException extends BusinessException {

    public NoLeftSeatException(String message) {
        super(message, ErrorCode.NO_LEFT_SEAT);
    }
}