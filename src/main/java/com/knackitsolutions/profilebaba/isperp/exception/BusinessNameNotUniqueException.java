package com.knackitsolutions.profilebaba.isperp.exception;

public class BusinessNameNotUniqueException extends CustomException{

  public BusinessNameNotUniqueException() {
    super("Business name must be unique", new Throwable("Business name already exists"));
  }
}
