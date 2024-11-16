package org.ktc2.cokaen.wouldyouin.reservation.exception;

import org.ktc2.cokaen.wouldyouin._common.exception.BusinessException;
import org.ktc2.cokaen.wouldyouin._common.exception.ErrorCode;

public class ReservationNotFoundForReviewException extends BusinessException {

    public ReservationNotFoundForReviewException(String message) {
        super(message, ErrorCode.RESERVATION_NOT_FOUND_FOR_REVIEW);
    }
}
