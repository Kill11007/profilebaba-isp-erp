package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.ErrorDTO;
import com.knackitsolutions.profilebaba.isperp.dto.ErrorResponse;
import com.knackitsolutions.profilebaba.isperp.exception.BusinessNameNotUniqueException;
import com.knackitsolutions.profilebaba.isperp.exception.InvalidLoginCredentialException;
import com.knackitsolutions.profilebaba.isperp.exception.NonRefreshableTokenException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.OTPNotSentException;
import com.knackitsolutions.profilebaba.isperp.exception.PhoneNumberAlreadyExistException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = InvalidLoginCredentialException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorResponse handleForbidden(InvalidLoginCredentialException exception, WebRequest request) {
    return new ErrorResponse(List.of(
        new ErrorDTO(findCode(), exception.getCause().getMessage(), exception.getMessage())));
  }

  @ExceptionHandler(value = BusinessNameNotUniqueException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleUniqueBusinessName(
      BusinessNameNotUniqueException businessNameNotUniqueException, WebRequest request) {
    return new ErrorResponse(List.of(
        new ErrorDTO(findCode(), businessNameNotUniqueException.getCause().getMessage(),
            businessNameNotUniqueException.getMessage())));
  }

  @ExceptionHandler(value = VendorNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleVendorNotFound(
      VendorNotFoundException vendorNotFoundException, WebRequest request) {
    return new ErrorResponse(List.of(
        new ErrorDTO(findCode(), vendorNotFoundException.getCause().getMessage(),
            vendorNotFoundException.getMessage())));
  }

  @ExceptionHandler(value = OTPNotSentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleOTPNotSent(
      OTPNotSentException otpNotSentException, WebRequest request) {
    return new ErrorResponse(List.of(
        new ErrorDTO(findCode(), otpNotSentException.getCause().getMessage(),
            otpNotSentException.getMessage())));
  }

  @ExceptionHandler(value = PhoneNumberAlreadyExistException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handlePhoneNumberExists(
      PhoneNumberAlreadyExistException phoneNumberAlreadyExistException, WebRequest request) {
    return new ErrorResponse(List.of(
        new ErrorDTO(findCode(), phoneNumberAlreadyExistException.getCause().getMessage(),
            phoneNumberAlreadyExistException.getMessage())));
  }

  @ExceptionHandler(value = NonRefreshableTokenException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleRefreshTokenException(NonRefreshableTokenException exception) {
    Throwable cause = exception.getCause();
    return new ErrorResponse(List.of(
        new ErrorDTO(findCode(), cause == null ? "" : cause.getMessage(),
            exception.getMessage())));
  }

  private String findCode() {
    //TODO
    return "";
  }
}
