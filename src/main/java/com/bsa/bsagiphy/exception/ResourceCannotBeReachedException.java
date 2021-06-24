package com.bsa.bsagiphy.exception;

public final class ResourceCannotBeReachedException extends RuntimeException {
    private static final String DEFAULT_MSG = "Resource is not accessible";

    public ResourceCannotBeReachedException() {
        super(DEFAULT_MSG);
    }
}
