package com.knackitsolutions.profilebaba.isperp.exception;

import lombok.Getter;

@Getter
public class InvalidLoginCredentialException extends CustomException{

  private static String defaultMessage = "Please enter correct username/password.";

  public InvalidLoginCredentialException() {
    super();
  }

  public InvalidLoginCredentialException(String message) {
    super(message);
  }

  public InvalidLoginCredentialException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidLoginCredentialException(Throwable cause) {
    super(defaultMessage, cause);
  }
}
