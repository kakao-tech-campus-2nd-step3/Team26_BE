package org.ktc2.cokaen.wouldyouin._common.exception;

import org.ktc2.cokaen.wouldyouin._common.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String notFoundedEntityName) {
        super(ErrorCode.ENTITY_NOT_FOUND, notFoundedEntityName);
    }
}