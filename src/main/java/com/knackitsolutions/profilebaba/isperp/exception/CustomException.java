package com.knackitsolutions.profilebaba.isperp.exception;

import java.util.function.Supplier;

public class CustomException extends RuntimeException{
    private static final String cause = "Internal Exception Occurred";
    private static final String defaultMessage = "Exception occurred in the backend.";
    private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);
    public CustomException() {
        super(defaultMessage, defaultCause.get());
    }

    public CustomException(String message) {
        super(message, defaultCause.get());
    }

    public CustomException(Throwable message) {
        super(defaultCause.get());
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
