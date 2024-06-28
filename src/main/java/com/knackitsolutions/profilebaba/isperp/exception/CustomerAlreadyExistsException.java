package com.knackitsolutions.profilebaba.isperp.exception;

import java.util.function.Supplier;

public class CustomerAlreadyExistsException extends RuntimeException{

  private static final String cause = "Resource already exists in database";
  private static final String defaultMessage = "Customer is already present in database. Please provide valid customer.";
  private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);

  public CustomerAlreadyExistsException() {
    super(defaultMessage, defaultCause.get());
  }

  public CustomerAlreadyExistsException(String message) {
    super(message, defaultCause.get());
  }

  public CustomerAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }

  public CustomerAlreadyExistsException(Throwable cause) {
    super(defaultMessage, cause);
  }
}
