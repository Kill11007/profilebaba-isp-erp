package com.knackitsolutions.profilebaba.isperp.exception;

import java.util.function.Supplier;

public class PermissionNotFoundException extends RuntimeException{
  private static final String cause = "Resource not found in db";
  private static final String defaultMessage = "Permission not present in database. Please provide valid Permission ID.";

  private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);

  public PermissionNotFoundException() {
    super(defaultMessage, defaultCause.get());
  }

  public PermissionNotFoundException(String message) {
    super(message, defaultCause.get());
  }

  public PermissionNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
