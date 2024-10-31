package org.ktc2.cokaen.wouldyouin._common.exception;

import org.ktc2.cokaen.wouldyouin._common.error.ErrorCode;

public class EntityParamIsNullException extends BusinessException{

    public EntityParamIsNullException(String entityName) {
        super(ErrorCode.ENTITY_PARAM_IS_NULL, entityName);
    }
}