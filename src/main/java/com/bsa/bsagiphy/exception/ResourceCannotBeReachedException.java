package com.bsa.bsagiphy.exception;

public final class ResourceCannotBeReachedException extends RuntimeException {
    private static final String DEFAULT_MSG = "Resource is not accessible";

    public ResourceCannotBeReachedException(Throwable cause) {
        super(DEFAULT_MSG, cause);
    }
}
