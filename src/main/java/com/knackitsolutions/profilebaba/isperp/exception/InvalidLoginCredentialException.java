package com.knackitsolutions.profilebaba.isperp.exception;

import lombok.Getter;

@Getter
public class InvalidLoginCredentialException extends CustomException{

  private String message = "Please enter correct username/password.";

  public InvalidLoginCredentialException() {
    super();
  }

  public InvalidLoginCredentialException(String message) {
    super(message);
    this.message = message;
  }

  public InvalidLoginCredentialException(String message, Throwable cause) {
    super(message, cause);
    this.message = message;
  }

  public InvalidLoginCredentialException(Throwable cause) {
    super(cause);
  }
}
