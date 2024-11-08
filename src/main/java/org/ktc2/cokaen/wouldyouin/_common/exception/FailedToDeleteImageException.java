package org.ktc2.cokaen.wouldyouin._common.exception;

import org.ktc2.cokaen.wouldyouin._common.error.ErrorCode;

public class FailedToDeleteImageException extends BusinessException {

    public FailedToDeleteImageException() {
        super(ErrorCode.FAIL_TO_DELETE_IMAGE, ErrorCode.FAIL_TO_DELETE_IMAGE.getMessage());
    }
}