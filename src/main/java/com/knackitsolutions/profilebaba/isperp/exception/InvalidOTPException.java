package com.knackitsolutions.profilebaba.isperp.exception;

public class InvalidOTPException extends CustomException{

  public InvalidOTPException() {
    super("Entered OTP is not valid.");
  }
}
