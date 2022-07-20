package com.knackitsolutions.profilebaba.isperp.exception;

public class PhoneNumberAlreadyExistException extends Exception{

  public PhoneNumberAlreadyExistException() {
    super("Phone number already exists.", new Throwable("Phone number already exists."));
  }

  public PhoneNumberAlreadyExistException(String message) {
    super(message);
  }

  public PhoneNumberAlreadyExistException(String message, Throwable cause) {
    super(message, cause);
  }

}
