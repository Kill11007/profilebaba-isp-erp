package com.knackitsolutions.profilebaba.isperp.exception;

import java.util.function.Supplier;

public class CustomerNotFoundException extends Exception{

  private static final String cause = "Resource not found in db";
  private static final String defaultMessage = "Customer not present in database. Please provide valid customer.";
  private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);

  public CustomerNotFoundException() {
    super(defaultMessage, defaultCause.get());
  }

  public CustomerNotFoundException(String message) {
    super(message, defaultCause.get());
  }

  public CustomerNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public CustomerNotFoundException(Throwable cause) {
    super(defaultMessage, cause);
  }
}
