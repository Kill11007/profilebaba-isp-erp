package com.knackitsolutions.profilebaba.isperp.exception;

import java.util.function.Supplier;

public class ServiceAreaAlreadyExistsException extends Exception{
  private static final String cause = "Resource already exists in db";
  private static final String defaultMessage = "ServiceArea already present in database. Please provide new ServiceArea.";

  private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);

  public ServiceAreaAlreadyExistsException() {
    super(defaultMessage, defaultCause.get());
  }

  public ServiceAreaAlreadyExistsException(String message) {
    super(message, defaultCause.get());
  }

  public ServiceAreaAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }
}
