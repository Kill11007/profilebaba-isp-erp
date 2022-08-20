package com.knackitsolutions.profilebaba.isperp.exception;

import java.util.function.Supplier;

public class EmployeeAlreadyExistsException extends Exception {
  private static final String cause = "Resource already exists in db";
  private static final String defaultMessage = "Employee already present in database. Please provide new Employee.";

  private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);

  public EmployeeAlreadyExistsException() {
    super(defaultMessage, defaultCause.get());
  }

  public EmployeeAlreadyExistsException(String message) {
    super(message, defaultCause.get());
  }

  public EmployeeAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }
}
