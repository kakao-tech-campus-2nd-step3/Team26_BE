package org.ktc2.cokaen.wouldyouin._common.exception;

import org.ktc2.cokaen.wouldyouin._common.error.ErrorCode;

public class CurrentLocationEmptyException extends BusinessException {

    public CurrentLocationEmptyException() {
        super(ErrorCode.CURRENT_LOCATION_EMPTY, "현재 위치 정보를 찾을 수 없습니다.");
    }
}
