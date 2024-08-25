package com.knackitsolutions.profilebaba.isperp.exception;

import java.util.function.Supplier;

public class EmployeeNotFoundException extends CustomException{
  private static final String cause = "Resource not found in db";
  private static final String defaultMessage = "Employee not present in database. Please provide valid Employee ID.";

  private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);

  public EmployeeNotFoundException() {
    super(defaultMessage, defaultCause.get());
  }

  public EmployeeNotFoundException(String message) {
    super(message, defaultCause.get());
  }

  public EmployeeNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
