package com.knackitsolutions.profilebaba.isperp.exception;

public class NonRefreshableTokenException extends RuntimeException{

  public NonRefreshableTokenException(String message) {
    super(message);
  }
}
