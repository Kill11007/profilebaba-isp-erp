package com.knackitsolutions.profilebaba.isperp.exception;

import java.util.function.Supplier;

public class PermissionAlreadyExistsException extends CustomException{
  private static final String cause = "Resource already exists in db";
  private static final String defaultMessage = "Permission already present in database. Please provide new permission.";

  private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);

  public PermissionAlreadyExistsException() {
    super(defaultMessage, defaultCause.get());
  }

  public PermissionAlreadyExistsException(String message) {
    super(message, defaultCause.get());
  }

  public PermissionAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }
}
