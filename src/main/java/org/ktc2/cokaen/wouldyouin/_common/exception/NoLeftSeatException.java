package org.ktc2.cokaen.wouldyouin._common.exception;

import org.ktc2.cokaen.wouldyouin._common.error.ErrorCode;

public class NoLeftSeatException extends BusinessException {

    public NoLeftSeatException(String message) {
        super(message, ErrorCode.NO_LEFT_SEAT);
    }
}