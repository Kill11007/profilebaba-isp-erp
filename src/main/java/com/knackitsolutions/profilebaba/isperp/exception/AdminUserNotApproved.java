package com.knackitsolutions.profilebaba.isperp.exception;

import java.util.function.Supplier;

public class AdminUserNotApproved extends RuntimeException{
    private static final String cause = "Admin user is not approved yet.";
    private static final String defaultMessage = "Admin user request has not been approved yet. Please contact admin support.";
    private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);

    public AdminUserNotApproved() {
        super(defaultMessage, defaultCause.get());
    }

    public AdminUserNotApproved(String message) {
        super(message, defaultCause.get());
    }

    public AdminUserNotApproved(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminUserNotApproved(Throwable cause) {
        super(defaultMessage, cause);
    }
}
