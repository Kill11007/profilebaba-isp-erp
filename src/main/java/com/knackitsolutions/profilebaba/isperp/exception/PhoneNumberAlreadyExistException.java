package com.knackitsolutions.profilebaba.isperp.exception;

public class PhoneNumberAlreadyExistException extends Exception{

  public PhoneNumberAlreadyExistException() {
    super("Phone number already exists.", new Throwable("Phone number already exists."));
  }

}
