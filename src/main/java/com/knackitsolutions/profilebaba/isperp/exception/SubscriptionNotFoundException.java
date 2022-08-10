package com.knackitsolutions.profilebaba.isperp.exception;

import java.util.function.Supplier;

public class SubscriptionNotFoundException extends RuntimeException{
  private static final String cause = "Resource not found in db";
  private static final String defaultMessage = "Subscription not present in database. Please provide valid subscription.";

  private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);

  public SubscriptionNotFoundException() {
    super(defaultMessage, defaultCause.get());
  }

  public SubscriptionNotFoundException(String message) {
    super(message, defaultCause.get());
  }

  public SubscriptionNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public SubscriptionNotFoundException(Throwable cause) {
    super(defaultMessage, cause);
  }

}
