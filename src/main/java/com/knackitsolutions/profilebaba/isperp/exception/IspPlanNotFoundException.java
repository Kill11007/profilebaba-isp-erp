package com.knackitsolutions.profilebaba.isperp.exception;

import java.util.function.Supplier;

public class IspPlanNotFoundException extends RuntimeException{
    private static final String cause = "Resource not found in db";
    private static final String defaultMessage = "Plan not present in database. Please provide valid Plan id.";

    private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);

    public IspPlanNotFoundException() {
        super(defaultMessage, defaultCause.get());
    }

    public IspPlanNotFoundException(String message) {
        super(message, defaultCause.get());
    }

    public IspPlanNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}