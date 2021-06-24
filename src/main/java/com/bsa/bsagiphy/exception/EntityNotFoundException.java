package com.bsa.bsagiphy.exception;

public final class EntityNotFoundException extends RuntimeException {
    private static final String DEFAULT_MSG = "Resource not found";

    public EntityNotFoundException(Throwable cause) {
        super(DEFAULT_MSG, cause);
    }
}
