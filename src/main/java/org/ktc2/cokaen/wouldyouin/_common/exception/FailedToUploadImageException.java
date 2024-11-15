package org.ktc2.cokaen.wouldyouin._common.exception;

public class FailedToUploadImageException extends BusinessException {

    public FailedToUploadImageException(String message) {
        super(message, ErrorCode.FAIL_TO_UPLOAD_IMAGE);
    }
}
