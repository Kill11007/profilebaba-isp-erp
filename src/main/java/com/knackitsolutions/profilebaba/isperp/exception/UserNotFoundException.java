package com.knackitsolutions.profilebaba.isperp.exception;

public class UserNotFoundException extends Exception{

  public UserNotFoundException() {
    super("User not found with the provide id");
  }

  public UserNotFoundException(String message) {
    super(message);
  }

  public static UserNotFoundException withId(Long id) {
    return new UserNotFoundException("User not found with id: " + id);
  }

  public static UserNotFoundException withPhoneNumber(String phoneNumber) {
    return new UserNotFoundException("User not found with phone number: " + phoneNumber);
  }

  public UserNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserNotFoundException(Throwable cause) {
    super(cause);
  }

  protected UserNotFoundException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
