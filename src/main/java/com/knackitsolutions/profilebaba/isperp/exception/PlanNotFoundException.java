package com.knackitsolutions.profilebaba.isperp.exception;

import java.util.function.Supplier;

public class PlanNotFoundException extends RuntimeException{

  private static final String cause = "Resource not found in db";
  private static final String defaultMessage = "Plan not present in database. Please provide valid plan.";

  private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);

  public PlanNotFoundException() {
    super(defaultMessage, defaultCause.get());
  }

  public PlanNotFoundException(String message) {
    super(message, defaultCause.get());
  }

  public PlanNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
