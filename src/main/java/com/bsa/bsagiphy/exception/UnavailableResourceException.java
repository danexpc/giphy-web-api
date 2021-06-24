package com.bsa.bsagiphy.exception;

public final class UnavailableResourceException extends RuntimeException {
    private static final String DEFAULT_MSG = "Resource is not accessible";

    public UnavailableResourceException(Throwable cause) {
        super(DEFAULT_MSG, cause);
    }
}
