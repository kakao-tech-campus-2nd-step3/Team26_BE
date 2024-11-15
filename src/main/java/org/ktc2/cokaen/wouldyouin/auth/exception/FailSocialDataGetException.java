package org.ktc2.cokaen.wouldyouin.auth.exception;

import org.ktc2.cokaen.wouldyouin._common.exception.BusinessException;
import org.ktc2.cokaen.wouldyouin._common.exception.ErrorCode;

public class FailSocialDataGetException extends BusinessException {

    public FailSocialDataGetException(String message) {
        super(message, ErrorCode.FAIL_SOCIAL_DATA_GET);
    }

}
