package com.knackitsolutions.profilebaba.isperp.exception;

import java.util.function.Supplier;

public class VendorNotFoundException extends RuntimeException{

  private static final String cause = "Resource not found in db";
  private static final String defaultMessage = "Vendor not present in database. Please provide valid vendor.";

  private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);

  public VendorNotFoundException() {
    super(defaultMessage, defaultCause.get());
  }

  public VendorNotFoundException(String message) {
    super(message, defaultCause.get());
  }
}
