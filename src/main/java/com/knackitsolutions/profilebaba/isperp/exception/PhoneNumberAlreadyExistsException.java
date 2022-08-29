package com.knackitsolutions.profilebaba.isperp.exception;

public class PhoneNumberAlreadyExistsException extends Exception{

  public PhoneNumberAlreadyExistsException() {
    super("Phone number already exists.", new Throwable("Phone number already exists."));
  }

  public PhoneNumberAlreadyExistsException(String message) {
    super(message);
  }

  public PhoneNumberAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }

}
