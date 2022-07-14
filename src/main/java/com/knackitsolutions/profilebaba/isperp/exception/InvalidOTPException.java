package com.knackitsolutions.profilebaba.isperp.exception;

public class InvalidOTPException extends Exception{

  public InvalidOTPException() {
    super("Entered OTP is not valid.");
  }
}
