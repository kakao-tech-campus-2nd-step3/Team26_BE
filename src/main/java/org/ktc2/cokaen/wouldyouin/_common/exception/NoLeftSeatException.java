package org.ktc2.cokaen.wouldyouin._common.exception;

import org.ktc2.cokaen.wouldyouin._common.error.ErrorCode;

public class NoLeftSeatException extends BusinessException {

    public NoLeftSeatException() {
        super(ErrorCode.NO_LEFT_SEAT);
    }
}