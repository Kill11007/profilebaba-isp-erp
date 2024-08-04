package com.knackitsolutions.profilebaba.isperp.exception;

public class OTPNotSentException extends RuntimeException {

  public OTPNotSentException() {
    super("OTP is not sent.");
  }

  public OTPNotSentException(String message) {
    super(message);
  }
}
