package org.ktc2.cokaen.wouldyouin.member.exception;

import org.ktc2.cokaen.wouldyouin._common.exception.BusinessException;
import org.ktc2.cokaen.wouldyouin._common.exception.ErrorCode;

public class NotLikeableMemberException extends BusinessException {
    public NotLikeableMemberException(String message) {
        super(message, ErrorCode.NOT_LIKEABLE_MEMBER_TYPE);
    }

}
