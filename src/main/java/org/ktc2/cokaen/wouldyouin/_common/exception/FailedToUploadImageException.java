package org.ktc2.cokaen.wouldyouin._common.exception;

import org.ktc2.cokaen.wouldyouin._common.error.ErrorCode;

public class FailedToUploadImageException extends BusinessException {

    public FailedToUploadImageException() {
        super(ErrorCode.FAIL_TO_UPLOAD_IMAGE, ErrorCode.FAIL_TO_UPLOAD_IMAGE.getMessage());
    }
}
