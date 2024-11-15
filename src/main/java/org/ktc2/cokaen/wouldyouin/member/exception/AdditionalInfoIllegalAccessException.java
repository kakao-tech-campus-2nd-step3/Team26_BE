package org.ktc2.cokaen.wouldyouin.member.exception;

import org.ktc2.cokaen.wouldyouin._common.exception.BusinessException;
import org.ktc2.cokaen.wouldyouin._common.exception.ErrorCode;

public class AdditionalInfoIllegalAccessException extends BusinessException {
        public AdditionalInfoIllegalAccessException(String message) {
            super(message, ErrorCode.ADDITIONAL_INFO_ILLEGAL_ACCESS);
        }
}
