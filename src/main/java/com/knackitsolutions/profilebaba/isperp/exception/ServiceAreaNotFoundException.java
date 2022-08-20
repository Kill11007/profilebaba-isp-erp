package com.knackitsolutions.profilebaba.isperp.exception;

import java.util.function.Supplier;

public class ServiceAreaNotFoundException extends Exception{
  private static final String cause = "Resource not found in db";
  private static final String defaultMessage = "ServiceArea not present in database. Please provide valid ServiceArea.";

  private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);

  public ServiceAreaNotFoundException() {
    super(defaultMessage, defaultCause.get());
  }

  public ServiceAreaNotFoundException(String message) {
    super(message, defaultCause.get());
  }

  public ServiceAreaNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
