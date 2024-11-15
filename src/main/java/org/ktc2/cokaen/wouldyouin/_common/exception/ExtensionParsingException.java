package org.ktc2.cokaen.wouldyouin._common.exception;

public class ExtensionParsingException extends BusinessException {

    public ExtensionParsingException(String message) {
        super(message, ErrorCode.URL_PARSING_FAILED);
    }
}
