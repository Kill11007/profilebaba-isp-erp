package com.knackitsolutions.profilebaba.isperp.exception;

import java.util.function.Supplier;

public class HardwareNotFoundException extends CustomException{

  private static final String cause = "Resource not found in database";
  private static final String defaultMessage = "Hardware not present in database. Please provide valid hardware.";

  private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);
  public HardwareNotFoundException() {
    super(defaultMessage, defaultCause.get());
  }

  public HardwareNotFoundException(String message) {
    super(message, defaultCause.get());
  }

  public HardwareNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public HardwareNotFoundException(Throwable cause) {
    super(defaultMessage, cause);
  }
}
